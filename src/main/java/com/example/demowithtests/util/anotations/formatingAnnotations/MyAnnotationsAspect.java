package com.example.demowithtests.util.anotations.formatingAnnotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class MyAnnotationsAspect {
    @Pointcut(value = "@annotation(InitMyAnnotations)")
    public void settingNamePointCut() {}

    @Around(value = "settingNamePointCut()")
    public Object formatAnnotatedFields(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InitMyAnnotations initAnnotation = signature.getMethod().getDeclaredAnnotation(InitMyAnnotations.class);
        List<Class<?>> workingAnnotations = List.of(initAnnotation.annotations());

        for (Object arg : joinPoint.getArgs()) {
            applyAnnotationsOnObject(arg, workingAnnotations);
        }
        return joinPoint.proceed();
    }

    private void applyAnnotationsOnObject(Object input, List<Class<?>> workingAnnotations) throws IllegalAccessException {
        if(input instanceof Collection){
            Collection<?> args = (Collection<?>) input;
            for (Object arg : args) {
                goThroughFields(arg, workingAnnotations);
            }
        } else if(input instanceof Object[]){
            Object[] args = (Object[]) input;
            for (Object arg : args) {
                goThroughFields(arg, workingAnnotations);
            }
        } else{
            goThroughFields(input, workingAnnotations);
        }
    }

    private void goThroughFields(Object arg, List<Class<?>> workingAnnotations) throws IllegalAccessException {
        for (Field field : arg.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(!(field.get(arg) instanceof Collection) && !field.getClass().isArray()){
                applyAnnotationsOnField(workingAnnotations, arg, field);
            }
            else{
                applyAnnotationsOnObject(field.get(arg), workingAnnotations);
            }
        }
    }

    private void applyAnnotationsOnField(List<Class<?>> workingAnnotations, Object arg, Field fields) throws IllegalAccessException {
        if (fields.isAnnotationPresent(Name.class) && workingAnnotations.contains(Name.class)) {
            if (fields.get(arg) instanceof String) fields.set(arg, toName((String) fields.get(arg)));
        }
        if (fields.isAnnotationPresent(ShortenCountry.class) && workingAnnotations.contains(ShortenCountry.class)) {
            if (fields.get(arg) instanceof String) fields.set(arg, toShortenCountry((String) fields.get(arg)));
        }
    }

    private String toName(String name) {
        char[] chars = name.toLowerCase().toCharArray();
        name = name.toUpperCase();
        chars[0] = name.charAt(0);
        return String.valueOf(chars);
    }

    private String toShortenCountry(String country) {
        if(!country.contains(" ")) return country.toUpperCase().substring(0, 2);

        else {
            StringBuilder result = new StringBuilder();
            String[] words = country.split(" ");
            for (String word : words) {
                String firstLetter = word.substring(0, 1);
                result.append(firstLetter.toUpperCase());
            }
            return String.valueOf(result);
        }
    }
}
