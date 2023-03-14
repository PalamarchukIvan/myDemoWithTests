package com.example.demowithtests.util.anotations;

import com.example.demowithtests.util.exception.AnnotatedFieldIsAbsentException;
import com.example.demowithtests.util.exception.BadParametersInActivateMyAnnotationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<Field> entityField = findAnnotatedField(entity.getDeclaredFields());
        if(entityField.isEmpty()) throw new BadParametersInActivateMyAnnotationException();

        List<Field> toEditFields = findFieldByName(parameter.getClass(), entityField);
        if(toEditFields.size() != entityField.size()) throw new AnnotatedFieldIsAbsentException();

        for (Field f : toEditFields) {
            f.setAccessible(true);
            f.set(parameter, toName((String)f.get(parameter)));
        }
        return joinPoint.proceed();
    }

    private List<Field> findAnnotatedField(Field[] fields) {
        List<Field> resultField = new ArrayList<>();
        for (Field f : fields) {
            if(Arrays.stream(f.getDeclaredAnnotations()).anyMatch(a -> a instanceof Name)){
                resultField.add(f);
            }
        }
        return resultField;
    }

    private List<Field> findFieldByName(Class<?> clazz, List<Field> targetFields){
        List<String> names = new ArrayList<>();
        for (Field targetField : targetFields) {
            names.add(targetField.getName());
        }
        List<Field> fields = new ArrayList<>(List.of(clazz.getDeclaredFields()));
        fields.removeIf(f -> !names.contains(f.getName()));
        return fields;
    }

    private String toName(String name) {
        char[] chars = name.toLowerCase().toCharArray();
        name = name.toUpperCase();
        chars[0] = name.charAt(0);
        return String.valueOf(chars);
    }
}
