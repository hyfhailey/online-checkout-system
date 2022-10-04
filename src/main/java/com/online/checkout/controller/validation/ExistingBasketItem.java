package com.online.checkout.controller.validation;

import com.online.checkout.controller.validation.validator.ExistingBasketItemValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.online.checkout.util.Errors.BASKET_ITEM_NOT_FOUND;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ExistingBasketItemValidator.class})
public @interface ExistingBasketItem {

    String message() default BASKET_ITEM_NOT_FOUND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
