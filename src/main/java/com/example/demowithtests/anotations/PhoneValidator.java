package com.example.demowithtests.anotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private Phone.Country from;
    @Override
    public void initialize(Phone constraintAnnotation) {
        from = constraintAnnotation.from();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.err.println(from);
        if(!s.matches("[0-9+]+")) return false;//проверяет нет ли символов,кроме цифр и +

        switch (from) {
            case UKRAINE: {
                return s.startsWith("+38") && s.length() == 13;
            }
            case POLAND: {
                return s.startsWith("+48") && s.length() == 13;
            }
            case GERMANY: {
                return s.startsWith("+49") && s.length() == 13;
            }
            case ANY: {
                return s.startsWith("+") && (s.length() > 7 && s.length() < 16);
            }
            default:
                throw new IllegalStateException("Unexpected value: " + from);
        }
    }
}
