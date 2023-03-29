package com.example.demowithtests.web;

import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeController {

    EmployeeReadDto saveEmployee(EmployeeDto requestForSave);

    EmployeeReadDto addPhotoToEmployee(MultipartFile image, Integer id) throws IOException, HttpMediaTypeNotSupportedException;

    List<PhotoDto> getAllPhotoFromEmployee(Integer id);

    //Получение списка юзеров
    List<EmployeeReadDto> getAllUsers();
    EmployeeReadDto updateBadge(Integer idEmployee, Integer idBadge);
    EmployeeReadDto updateBadgeInstantly(Integer idEmployee, BadgeRequestDto badge);

    Page<EmployeeReadDto> getPage(int page, int size);

    EmployeeReadDto getEmployeeById(Integer id);

    EmployeeReadDto refreshEmployeePut(Integer id, EmployeeForPatchDto employee);

    EmployeeReadDto refreshEmployeePatch(Integer id, EmployeeForPatchDto employee);

    void removeEmployeeById(Integer id);

    void removeAllUsers();

    List<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    List<EmployeeReadDto> getAllUsersWithAddresses();

    List<EmployeeReadDto> getAllUsersByNamePartly(String letters);

    void generateTestSetOfEntities(int number);

    List<EmployeeReadDto> getAllWithExpiredPhotos();

    List<EmployeeReadDto> notifyAllWithExpiredPhotos();
    void removePhoto(@PathVariable Integer id);

    byte[] getPhoto(@PathVariable Integer id);
}
