package com.example.demowithtests.web.EmployeeController;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.BadgeDto.BadgeRequestDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto.PhotoDto;
import com.example.demowithtests.service.EmployeeService.EmployeeService;
import com.example.demowithtests.service.PhotoService.PhotoServiceBean;
import com.example.demowithtests.util.config.MapStruct.BadgeMapper;
import com.example.demowithtests.util.config.MapStruct.EmployeeMapper;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeControllerSwagger {
    private final EmployeeService employeeService;
    private final PhotoServiceBean photoService;

    //Операция сохранения юзера в базу данных
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        var employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(requestForSave);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.create(employee));
    }

    @PostMapping(value = "/employees/addPhoto/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addPhotoToEmployee(@RequestParam MultipartFile image, @PathVariable Integer id) throws IOException, HttpMediaTypeNotSupportedException {
        if (!Objects.equals(image.getContentType(), "image/png") && !Objects.equals(image.getContentType(), "image/jpeg"))
            throw new HttpMediaTypeNotSupportedException("photo must be .png / .jpeg");
        Employee employee = employeeService.getById(id);
        photoService.addPhoto(image, employeeService.getById(id));
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employee);
    }

    @GetMapping("/employees/getPhotos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDto> getAllPhotoFromEmployee(@PathVariable Integer id) {
        EmployeeDto employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(employeeService.getById(id));
        return employee.photos;
    }

    //Получение списка юзеров
    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.getAll());
    }

    @PatchMapping("/employees/{idEmployee}/badge/{idBadge}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto updateBadge(@PathVariable Integer idEmployee, @PathVariable Integer idBadge) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.addBadge(idEmployee, idBadge));
    }

    @PatchMapping("/employees/{idEmployee}/badge")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto updateBadgeInstantly(@PathVariable Integer idEmployee, @RequestBody BadgeRequestDto badge) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.addBadge(idEmployee, BadgeMapper.INSTANCE.badgeRequestDtoToBadge(badge)));
    }

    @PatchMapping("/employees/{idEmployee}/badge/inheritance")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto updateBadgeInherently(@PathVariable Integer idEmployee,
                                                 @RequestParam(required = false, name = "reason", defaultValue = "CHANGED") Badge.State reason) {
        System.err.println("controller: " + reason);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.updateBadge(idEmployee, reason));
    }

    @GetMapping("/employees/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.getAllWithPagination(paging));
    }

    //Получения юзера по id
    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        if (employee.getIsPrivate()) throw new ResourceIsPrivateException();
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employee);
    }

    //Обновление юзера
    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePut(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeForPatchDto employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.repostById(id, EmployeeMapper.INSTANCE.employeeForPatchDtoToEmployee(employee)));
    }

    @PatchMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePatch(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeForPatchDto employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.patchById(id, EmployeeMapper.INSTANCE.employeeForPatchDtoToEmployee(employee)));
    }

    //Удаление по id
    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/employees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/employees/country")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString()).toList());
    }

    @GetMapping("/employees/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersWithAddresses() throws NoSuchMethodException {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.findEmployeeIfAddressPresent());
    }

    @GetMapping("/employees/char")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersByNamePartly(@RequestParam String letters) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.findEmployeeByPartOfTheName(letters));
    }

    @PostMapping("/employees/generation/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void generateTestSetOfEntities(@PathVariable int number) {
        employeeService.generateTestDatabase(number);
    }

    @GetMapping("/employees/expired_photos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllWithExpiredPhotos() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.findEmployeesWithExpiredPhotos());
    }

    @GetMapping(value = "/employees/photo/{id}",
            produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhoto(@PathVariable Integer id) {
        return photoService.findPhoto(id);
    }

    @GetMapping("/employees/notify_photos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> notifyAllWithExpiredPhotos() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.updateEmployeesWithExpiredPhotos());
    }

    @DeleteMapping("/employees/photo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removePhoto(@PathVariable Integer id) {
        photoService.deletePhoto(id);
    }
}
