package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PhotoService {
    void addPhoto(MultipartFile file, Employee employee) throws IOException;
}
