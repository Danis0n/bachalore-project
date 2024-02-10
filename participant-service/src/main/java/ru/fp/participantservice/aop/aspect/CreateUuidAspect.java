package ru.fp.participantservice.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.fp.participantservice.aop.annotaion.AddUuid;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Aspect
@Component
public class CreateUuidAspect {
    @Around("@annotation(ru.fp.participantservice.aop.annotaion.RequestUuid)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] modifiedArgs = joinPoint.getArgs();
        int index = 0;

        for (Object arg : joinPoint.getArgs()) {
            Class<?> clazz = arg.getClass();
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof AddUuid) {
                    Field field = clazz.getDeclaredField(((AddUuid) annotation).name());
                    field.setAccessible(true);
                    field.set(arg, java.util.UUID.randomUUID().toString());
                    modifiedArgs[index] = arg;
                }
            }
            index++;
        }

        return joinPoint.proceed(modifiedArgs);
    }
}
