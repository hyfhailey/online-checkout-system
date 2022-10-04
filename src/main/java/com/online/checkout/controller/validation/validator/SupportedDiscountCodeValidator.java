package com.online.checkout.controller.validation.validator;

import com.online.checkout.controller.validation.ExistingDiscountCode;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.online.checkout.util.DiscountCodeRegexes.*;

@Component
public class SupportedDiscountCodeValidator implements ConstraintValidator<ExistingDiscountCode, String> {

    @Override
    public boolean isValid(String discountCode, ConstraintValidatorContext constraintValidatorContext) {
        return BUY_NUM_GET_NUM_FREE.matcher(discountCode).find()
                || BUY_NUM_GET_PERCENT_OFF_THE_NUM.matcher(discountCode).find()
                || PERCENT_OFF.matcher(discountCode).find();
    }
}