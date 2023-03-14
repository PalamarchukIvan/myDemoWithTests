package com.example.demowithtests.util.anotations;

import com.example.demowithtests.util.exception.AnnotatedFieldIsAbsentException;
import com.example.demowithtests.util.exception.BadParametersInActivateMyAnnotationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
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
        if(parameter == null) throw new BadParametersInActivateMyAnnotationException();

        Field employeeField = findAnnotatedField(entity.getDeclaredFields()) ;
        if(employeeField == null) throw new BadParametersInActivateMyAnnotationException();

        Field toEditField = findFieldByName(parameter.getClass(), employeeField.getName());
        if(toEditField == null) throw new AnnotatedFieldIsAbsentException();

        toEditField.setAccessible(true);
        toEditField.set(parameter, toName((String)toEditField.get(parameter)));
        return joinPoint.proceed();
    }
    private Field findAnnotatedField(Field[] fields) {
        for (Field f : fields) {
            if(Arrays.stream(f.getDeclaredAnnotations()).anyMatch(a -> a instanceof Name)){
                return f;
            }
        }
        return null;
    }

    private Field findFieldByName(Class<?> clazz, String name){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equals(name)){
                return field;
            }
        }
        return null;
    }

    private String toName(String name) {
        char[] chars = name.toLowerCase().toCharArray();
        name = name.toUpperCase();
        chars[0] = name.charAt(0);
        return String.valueOf(chars);
    }
}
