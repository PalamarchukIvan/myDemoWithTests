package com.example.demowithtests.util.anotations.formatingAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitMyAnnotations {
    Class<?>[] annotations() default {Name.class, ShortenCountry.class};
}
