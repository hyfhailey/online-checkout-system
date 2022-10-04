package com.online.checkout.controller.validation;

import com.online.checkout.controller.validation.validator.ExistingProductValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.online.checkout.util.Errors.PRODUCT_NOT_FOUND;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingProductValidator.class})
public @interface ExistingProduct {

    String message() default PRODUCT_NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
