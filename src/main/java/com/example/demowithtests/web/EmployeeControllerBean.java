package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.*;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.PhotoServiceBean;
import com.example.demowithtests.util.anotations.validation.Image;
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
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeControllerSwagger {
    private final EmployeeService employeeService;
    private final PhotoServiceBean photoService;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        var employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(requestForSave);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.create(employee));
    }

    @PostMapping(value = "/users/addPhoto/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addPhotoToEmployee(@RequestParam MultipartFile image, @PathVariable Integer id) throws IOException, HttpMediaTypeNotSupportedException {
        if (!Objects.equals(image.getContentType(), "image/png") && !Objects.equals(image.getContentType(), "image/jpeg"))
            throw new HttpMediaTypeNotSupportedException("photo must be .png / .jpeg");
        Employee employee = employeeService.getById(id);
        photoService.addPhoto(image, employeeService.getById(id));
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employee);
    }

    @GetMapping("/users/getPhotos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDto> getAllPhotoFromEmployee(@PathVariable Integer id) {
        EmployeeDto employee = EmployeeMapper.INSTANCE.employeeToEmployeeDto(employeeService.getById(id));
        return employee.photos;
    }
    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.filterPrivateEmployees(employeeService.getAll()));
    }
    @PatchMapping("/users/{idEmployee}/badge/{idBadge}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto updateBadge(@PathVariable Integer idEmployee, @PathVariable Integer idBadge) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.addBadge(idEmployee, idBadge));
    }

    @PatchMapping("/users/{idEmployee}/badge/")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto updateBadgeInstantly(@PathVariable Integer idEmployee, @RequestBody BadgeRequestDto badge) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.addBadge(idEmployee, BadgeMapper.INSTANCE.badgeRequestDtoToBadge(badge)));
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.getAllWithPagination(paging));
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        if (employee.getIsPrivate()) throw new ResourceIsPrivateException();
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employee);
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePut(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeForPatchDto employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.repostById(id, EmployeeMapper.INSTANCE.employeeForPatchDtoToEmployee(employee)));
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePatch(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeForPatchDto employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.patchById(id, EmployeeMapper.INSTANCE.employeeForPatchDtoToEmployee(employee)));
    }

    //Удаление по id
    @PatchMapping("/users/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString()).toList());
    }

    @GetMapping("/users/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersWithAddresses() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.filterPrivateEmployees(employeeService.findEmployeeIfAddressPresent()));
    }

    @GetMapping("/users/char")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersByNamePartly(@RequestParam String letters) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.filterPrivateEmployees(employeeService.findEmployeeByPartOfTheName(letters)));
    }

    @PostMapping("/users/generation/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void generateTestSetOfEntities(@PathVariable int number) {
        employeeService.generateTestDatabase(number);
    }

    @GetMapping("/users/expired_photos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllWithExpiredPhotos() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.findEmployeesWithExpiredPhotos());
    }

    @GetMapping(value = "/users/photo/{id}",
            produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhoto(@PathVariable Integer id) {
        return photoService.findPhoto(id);
    }

//    @GetMapping("/test")
//    public String test(){
//        return "<!DOCTYPE html>\n"+
//                "<html>\n" +
//                "<head></head>\n"+
//                "<body>\n" +
//                "\t<img src = \"C:\\Users\\Иван\\Desktop\\Hillel\\myDemoWithTests\\employee-photos\\employee-1\\employee-1 7.png\">\n" +
//                "</body>\n" +
//                "</html>\n";
//    }

    @GetMapping("/users/notify_photos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> notifyAllWithExpiredPhotos() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.updateEmployeesWithExpiredPhotos());
    }

    @DeleteMapping("/users/photo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removePhoto(@PathVariable Integer id) {
        photoService.deletePhoto(id);
    }
}
