package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PhotoRepository;
import com.example.demowithtests.util.FileUploadManager;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
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
    private final EmployeeRepository employeeRepository;
    private final PhotoRepository photoRepository;

    @Override
    public void addPhoto(MultipartFile file, Employee employee) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String path = "employee-photos/employee-" + employee.getId();
        Integer lastPhotoId = photoRepository.findLastPhotoId();
        employee.getPhotos().add(Photo.builder()
                .name(employee.getName() + " " + lastPhotoId)
                .path(path)
                .format(fileName.split("\\.")[1])
                .isPrivate(Boolean.FALSE)
                .uploadDate(LocalDate.now())
                .url("http://localhost:8087/api/users/photo/" + (lastPhotoId == null ? 1 : lastPhotoId + 1))
                .bytes(file.getBytes())
                .build());
        employeeRepository.save(employee);
        String newFileName = "employee-" + employee.getId() + " " + employee.getPhotos().size() + "." + fileName.split("\\.")[1];
        FileUploadManager.saveFile(newFileName, "employee-photos/employee-" + employee.getId(), file);
    }

    public void deletePhoto(Integer id) {
        Photo photo = photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new) ;
        photo.setIsPrivate(Boolean.TRUE);
        photoRepository.save(photo);
    }

    @Override
    public byte[] findPhoto(Integer photo_id) {
        Photo result = photoRepository.findById(photo_id).orElseThrow(ResourceNotFoundException::new);
        if(result.getIsPrivate()) throw new ResourceIsPrivateException();
        return result.getBytes();
    }
}
