package com.example.demowithtests.web.EmployeeController;

import com.example.demowithtests.dto.EmployeeDto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeDto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto.PhotoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    @Operation(summary = "This is endpoint to add a Photo to employee.", description = "Ads profile picture to employee.", tags = {"Employee", "Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto addPhotoToEmployee(MultipartFile image, Integer id) throws IOException, HttpMediaTypeNotSupportedException;

    @Override
    @Operation(summary = "This is endpoint adds badge to Employee.", description = "Adds badge to employee.", tags = {"Employee", "Badge"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto updateBadge(Integer idEmployee, Integer idBadge);

    @Override
    @Operation(summary = "This is endpoint gets all employees.", description = "Shows all employees in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsers();

    @Override
    @Operation(summary = "This is endpoint gets employees with pagination.", description = "Shows all employees in DB  with pagination", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Page<EmployeeReadDto> getPage(int page, int size);

    @Override
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeReadDto getEmployeeById(Integer id);

    @Override
    @Operation(summary = "This is endpoint replace employee with another.", description = "PUT for employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto refreshEmployeePut(Integer id, EmployeeForPatchDto employee);

    @Override
    @Operation(summary = "This is endpoint replace part of employee with another.", description = "PATCH for employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto refreshEmployeePatch(Integer id, EmployeeForPatchDto employee);

    @Override
    @Operation(summary = "This is endpoint deletes employee", description = "DELETE for employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeEmployeeById(Integer id);

    @Override
    @Operation(summary = "RED BUTTON, DON'T PRESS", description = "Drops  database", tags = {"DB"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeAllUsers();

    @Override
    @Operation(summary = "This is endpoint returns employee from specific country with pagination", description = "find employee based on country", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Override
    @Operation(summary = "This is endpoint gets all employees with address present", description = "find all employees with addresses", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsersWithAddresses() throws NoSuchMethodException;

    @Override
    @Operation(summary = "This is endpoint returns employees with specified name containing", description = "find employee based on name", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsersByNamePartly(String letters);

    @Override
    @Operation(summary = "This is endpoint generate test sample of employees", description = "generate {number} of employees", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void generateTestSetOfEntities(int number);

    @Override
    @Operation(summary = "This is endpoint find all employees with expired photos", description = "find all with expired photo", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllWithExpiredPhotos();

    @Override
    @Operation(summary = "This is endpoint sends email to all employees with expired photos", description = "sends email to those, whose photo is expired", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> notifyAllWithExpiredPhotos();

    @Override
    @Operation(summary = "This is endpoint shows all photos from employee", description = "shows employees' photos", tags = {"Photo", "Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<PhotoDto> getAllPhotoFromEmployee(Integer id);

    @Override
    @Operation(summary = "This is endpoint shows specific photo", description = "shows photo", tags = {"Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    byte[] getPhoto(Integer id);

    @Override
    @Operation(summary = "This is endpoint removes photo with {id}", description = "removes photo", tags = {"Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removePhoto(@PathVariable Integer id);
}
