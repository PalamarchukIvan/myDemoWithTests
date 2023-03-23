package com.example.demowithtests.util.anotations.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {
    @Override
    public void initialize(Image constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(image.getContentType(), "image/png") && Objects.equals(image.getContentType(), "image/jpeg");
    }
}
