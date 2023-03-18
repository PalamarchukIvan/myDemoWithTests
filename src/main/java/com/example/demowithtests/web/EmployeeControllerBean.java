package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeControllerSwagger {
    private final EmployeeService employeeService;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto saveEmployee( @RequestBody @Valid EmployeeDto requestForSave) {
        var employee = EmployeeMapper.INSTANCE.employeeDtoToEmployee(requestForSave);
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.create(employee));
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.filterPrivateEmployees(employeeService.getAll()));
    }
    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return  EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.getAllWithPagination(paging));
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        if(employee.getIsPrivate()) throw new ResourceIsPrivateException();
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

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @GetMapping("/users/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersWithAddresses() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.filterPrivateEmployees(employeeService.findEmployeeIfAddressPresent()));
    }

    @GetMapping("/users/char")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsersByNamePartly(@RequestParam String letters){
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

    @GetMapping("/users/notify_photos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> notifyAllWithExpiredPhotos() {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employeeService.updateEmployeesWithExpiredPhotos());
    }
}
