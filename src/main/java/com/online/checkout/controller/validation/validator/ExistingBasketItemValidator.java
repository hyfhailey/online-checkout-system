package com.online.checkout.controller.validation.validator;

import com.online.checkout.controller.validation.ExistingBasketItem;
import com.online.checkout.data.repository.BasketItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
public class ExistingBasketItemValidator implements ConstraintValidator<ExistingBasketItem, UUID> {
    @Autowired
    BasketItemsRepository basketItemsRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return basketItemsRepository.findById(uuid).isPresent();
    }
}