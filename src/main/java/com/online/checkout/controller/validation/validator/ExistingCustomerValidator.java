package com.online.checkout.controller.validation.validator;

import com.online.checkout.controller.validation.ExistingCustomer;
import com.online.checkout.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.UUID;

@Component
public class ExistingCustomerValidator implements ConstraintValidator<ExistingCustomer, UUID> {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return customerRepository.findById(uuid).isPresent();
    }
}
