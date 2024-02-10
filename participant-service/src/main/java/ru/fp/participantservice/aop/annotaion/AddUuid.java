package ru.fp.participantservice.aop.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the class has UUID.
 * This annotation is applied to the entity class for automatic generation of uuids.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AddUuid {

    /**
     * (Optional) The field name. Defaults to the unqualified
     * name of the uuid field. This name is used to refer
     * to inject UUID into the custom field.
     */
    String name() default "uuid";
}
