package com.example.demowithtests.util.anotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Aspect
@Component
public class MyAnnotationsAspect {
    @Pointcut(value = "@annotation(InitMyAnnotations)")
    public void settingNamePointCut(){}
    @Around(value = "settingNamePointCut()")
    public Object formatAnnotatedFields(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InitMyAnnotations initAnnotation = signature.getMethod().getDeclaredAnnotation(InitMyAnnotations.class);
        List<Class<?>> workingAnnotations = List.of(initAnnotation.annotations());

        for (Object arg: joinPoint.getArgs()) {
            for (Field argField : arg.getClass().getDeclaredFields()) {
                if(argField.isAnnotationPresent(Name.class) && workingAnnotations.contains(Name.class)){
                    argField.setAccessible(true);
                    argField.set(arg, toName((String) argField.get(arg)));
                }
                if(argField.isAnnotationPresent(ShortenCountry.class) && workingAnnotations.contains(ShortenCountry.class)){
                    argField.setAccessible(true);
                    argField.set(arg, toShortenCountry((String) argField.get(arg)));
                }
            }
        }
        return joinPoint.proceed();
    }
    private String toName(String name) {
        char[] chars = name.toLowerCase().toCharArray();
        name = name.toUpperCase();
        chars[0] = name.charAt(0);
        return String.valueOf(chars);
    }

    private String toShortenCountry(String country){
        return country.toUpperCase().substring(0, 2);
    }
}
