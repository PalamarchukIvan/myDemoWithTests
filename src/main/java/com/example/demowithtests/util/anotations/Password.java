package com.example.demowithtests.util.anotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String regex() default "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";//1 цирфа, 1 буква латинксого алфавита, длина 6+
    String message() default "Password does not obey constrains";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
