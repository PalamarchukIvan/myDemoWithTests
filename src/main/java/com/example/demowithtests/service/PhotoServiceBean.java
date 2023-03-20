package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.FileUploadManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServiceBean implements PhotoService {
    private final EmployeeRepository repository;
    @Override
    public void addPhoto(MultipartFile file, Employee employee) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        employee.getPhotos().add(Photo.builder()
                .format(fileName.split("\\.")[1])
                .isPrivate(Boolean.FALSE)
                .uploadDate(LocalDate.now())
                .metaDate(file.getResource().toString())
                .build());
        repository.save(employee);
        String newFileName =  "employee-" + employee.getId() + "." + fileName.split("\\.")[1];
        FileUploadManager.saveFile(newFileName, "employee-photos/employee-" + employee.getId(), file);
        System.err.println(employee.getPhotos().get(0).toString());
    }


}
