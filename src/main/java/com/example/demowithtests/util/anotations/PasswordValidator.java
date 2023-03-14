package com.example.demowithtests.util.anotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private String regexp;
    @Override
    public void initialize(Password constraintAnnotation) {
        regexp = constraintAnnotation.regex();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.matches(regexp);
    }
}
