package com.ecommerce.customAnnotation;

import com.ecommerce.customAnnotation.impl.UniqueConstraintValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {

    String message() default "Please enter unique value.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}