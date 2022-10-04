package com.online.checkout.controller.validation.validator;

import com.online.checkout.controller.validation.ExistingProduct;
import com.online.checkout.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistingProductValidator implements ConstraintValidator<ExistingProduct, Integer> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return productRepository.findById(value).isPresent();
    }
}
