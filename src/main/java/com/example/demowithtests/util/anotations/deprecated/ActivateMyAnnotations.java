package com.example.demowithtests.util.anotations.deprecated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ActivateMyAnnotations {
    Class<?> entity();
    Class<?> dto();
}