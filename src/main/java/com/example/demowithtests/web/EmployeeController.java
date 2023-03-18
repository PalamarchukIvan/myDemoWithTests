package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeForPatchDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EmployeeController {

    EmployeeReadDto saveEmployee(EmployeeDto requestForSave);

    //Получение списка юзеров
    List<EmployeeReadDto> getAllUsers();

    Page<EmployeeReadDto> getPage(int page, int size);

    EmployeeReadDto getEmployeeById(Integer id);

    EmployeeReadDto refreshEmployeePut(Integer id, EmployeeForPatchDto employee);

    EmployeeReadDto refreshEmployeePatch(Integer id, EmployeeForPatchDto employee);

    void removeEmployeeById(Integer id);

    void removeAllUsers();

    List<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    List<String> getAllUsersC();

    List<String> getAllUsersSort();

    Optional<String> getAllUsersSo();

    List<EmployeeReadDto> getAllUsersWithAddresses();

    List<EmployeeReadDto> getAllUsersByNamePartly(String letters);

    void generateTestSetOfEntities(int number);

    List<EmployeeReadDto> getAllWithExpiredPhotos();

    List<EmployeeReadDto> notifyAllWithExpiredPhotos();
}
