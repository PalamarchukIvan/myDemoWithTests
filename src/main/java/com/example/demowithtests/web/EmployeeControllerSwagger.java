package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EmployeeControllerSwagger extends EmployeeController{

    @Override
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto saveEmployee(EmployeeDto requestForSave);

    @Override
    List<EmployeeReadDto> getAllUsers();

    @Override
    Page<EmployeeReadDto> getPage(int page, int size);

    @Override
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto getEmployeeById(Integer id);

    @Override
    EmployeeReadDto refreshEmployeePut(Integer id, EmployeeForPatchDto employee);

    @Override
    EmployeeReadDto refreshEmployeePatch(Integer id, EmployeeForPatchDto employee);

    @Override
    void removeEmployeeById(Integer id);

    @Override
    void removeAllUsers();

    @Override
    List<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Override
    List<String> getAllUsersC();

    @Override
    List<String> getAllUsersSort();

    @Override
    Optional<String> getAllUsersSo();

    @Override
    List<EmployeeReadDto> getAllUsersWithAddresses();

    @Override
    List<EmployeeReadDto> getAllUsersByNamePartly(String letters);

    @Override
    void generateTestSetOfEntities(int number);

    @Override
    List<EmployeeReadDto> getAllWithExpiredPhotos();

    @Override
    List<EmployeeReadDto> notifyAllWithExpiredPhotos();
}
