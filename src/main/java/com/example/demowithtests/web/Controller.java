package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.MapStruct.EmployeeMapper;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class Controller {

    private final EmployeeService employeeService;
    //private final EmployeeConverter converter;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
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
        return  EmployeeMapper.employeeToEmployeeReadDto(employeeService.getAllWithPagination(paging));
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        if(employee.getIsPrivate()) throw new ResourceIsPrivateException();
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(employee);
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePut(@PathVariable("id") Integer id, @RequestBody EmployeeForPatchDto employee) {
        return EmployeeMapper.INSTANCE.employeeToEmployeeReadDto(
                employeeService.repostById(id, EmployeeMapper.INSTANCE.employeeForPatchDtoToEmployee(employee)));
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployeePatch(@PathVariable("id") Integer id, @RequestBody EmployeeForPatchDto employee) {
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
}
