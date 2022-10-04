package com.online.checkout.controller.validation.validator;

import com.online.checkout.controller.validation.ExistingBasket;
import com.online.checkout.data.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
public class ExistingBasketValidator implements ConstraintValidator<ExistingBasket, UUID> {
    @Autowired
    BasketRepository basketRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return basketRepository.findById(uuid).isPresent();
    }
}
