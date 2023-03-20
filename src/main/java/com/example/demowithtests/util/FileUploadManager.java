package com.example.demowithtests.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadManager {
    public static void saveFile(String name, String path, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(path);
        if(!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(name);
        Files.copy(file.getInputStream(), filePath);
    }
}
