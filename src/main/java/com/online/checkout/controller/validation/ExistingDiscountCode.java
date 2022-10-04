package com.online.checkout.controller.validation;

import com.online.checkout.controller.validation.validator.SupportedDiscountCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.online.checkout.util.Errors.DISCOUNT_CODE_INVALID;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SupportedDiscountCodeValidator.class})
public @interface ExistingDiscountCode {

    String message() default DISCOUNT_CODE_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

