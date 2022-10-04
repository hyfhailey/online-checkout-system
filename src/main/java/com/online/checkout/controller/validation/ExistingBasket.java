package com.online.checkout.controller.validation;

import com.online.checkout.controller.validation.validator.ExistingBasketValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.online.checkout.util.Errors.BASKET_NOT_FOUND;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingBasketValidator.class})
public @interface ExistingBasket {

    String message() default BASKET_NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
