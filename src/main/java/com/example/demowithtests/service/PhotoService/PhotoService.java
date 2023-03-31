package com.example.demowithtests.service.PhotoService;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PhotoService {
    void addPhoto(MultipartFile file, Employee employee) throws IOException;
    byte[] findPhoto(Integer photo_id);
}
