package com.example.demowithtests.util.anotations;

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
    @Pointcut(value = "@annotation(com.example.demowithtests.util.anotations.InitMyAnnotations)")
    public void settingNamePointCut() {}

    @Around(value = "settingNamePointCut()")
    public Object formatAnnotatedFields(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InitMyAnnotations initAnnotation = signature.getMethod().getDeclaredAnnotation(InitMyAnnotations.class);
        List<Class<?>> workingAnnotations = List.of(initAnnotation.annotations());

        for (Object arg : joinPoint.getArgs()) {
            for (Field argField : arg.getClass().getDeclaredFields()) {
                argField.setAccessible(true);
                if(argField.get(arg) instanceof Collection){
                    for (Object innerArgs : (Collection) argField.get(arg)) {
                        for (Field innerFields : innerArgs.getClass().getDeclaredFields()){
                            innerFields.setAccessible(true);
                            applyAnnotations(workingAnnotations, innerArgs, innerFields);
                        }
                    }
                }
                else {
                    applyAnnotations(workingAnnotations, arg, argField);
                }
            }
        }
        return joinPoint.proceed();
    }

    private void applyAnnotations(List<Class<?>> workingAnnotations, Object innerArgs, Field innerFields) throws IllegalAccessException {
        if (innerFields.isAnnotationPresent(Name.class) && workingAnnotations.contains(Name.class)) {
            if (innerFields.get(innerArgs) instanceof String) innerFields.set(innerArgs, toName((String) innerFields.get(innerArgs)));
        }
        if (innerFields.isAnnotationPresent(ShortenCountry.class) && workingAnnotations.contains(ShortenCountry.class)) {
            if (innerFields.get(innerArgs) instanceof String) innerFields.set(innerArgs, toShortenCountry((String) innerFields.get(innerArgs)));
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
