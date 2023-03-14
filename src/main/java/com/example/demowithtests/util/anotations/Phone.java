package com.example.demowithtests.util.anotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    Country from() default Country.ANY;
    String message() default "Phone must start with '+', and be according to specified standard";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    enum Country{
        UKRAINE("Ukraine"), POLAND("Poland"), GERMANY("Germany"), ANY("World");
        final String country;
        Country(String country) {
            this.country = country;
        }
    }
}
