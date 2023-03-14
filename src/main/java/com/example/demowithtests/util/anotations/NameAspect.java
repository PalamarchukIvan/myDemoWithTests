package com.example.demowithtests.util.anotations;

import com.example.demowithtests.util.exception.BadParametersInActivateMyAnnotationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class NameAspect {
    @Pointcut(value = "@annotation(com.example.demowithtests.util.anotations.ActivateMyAnnotations)")
    public void settingNamePointCut(){}

    @Around(value = "settingNamePointCut()")
    public Object formatName(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> declaredFrom = joinPoint.getSignature().getDeclaringType();
        Class<?>[] parametersClass = new Class[joinPoint.getArgs().length];
        for (int i = 0; i < parametersClass.length; i++) {
            parametersClass[i] = joinPoint.getArgs()[i].getClass();
        }

        Method declaredIn = declaredFrom.getDeclaredMethod(joinPoint.getSignature().getName(), parametersClass);
        Class<?> entity = declaredIn.getAnnotation(ActivateMyAnnotations.class).entity();
        Class<?> dto = declaredIn.getAnnotation(ActivateMyAnnotations.class).dto();

        Object[] args = joinPoint.getArgs();
        Object parameter = null;
        for (Object arg : args) {
            if(arg.getClass() == dto){
                parameter = arg;
            }
        }
        if(parameter == null){
            throw new BadParametersInActivateMyAnnotationException();
        }
        Field[] employeeField = entity.getDeclaredFields();
        for (Field f : employeeField) {
            if(Arrays.stream(f.getDeclaredAnnotations()).anyMatch(a -> a instanceof Name)){
                Field[] employeeDtoFields = parameter.getClass().getDeclaredFields();
                for (Field filed : employeeDtoFields) {
                    if(filed.getName().equals(f.getName())){
                        filed.setAccessible(true);
                        filed.set(parameter, toName((String)filed.get(parameter)));
                    }
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
}
