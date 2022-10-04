package com.online.checkout.business.discount.strategy;

import com.online.checkout.data.enu.DiscountType;
import com.online.checkout.data.model.DiscountCalculation;

public interface DiscountStrategy {
    DiscountCalculation applyTo(DiscountCalculation discountCalculation);

    boolean applyAloneOnly();

    DiscountType getType();

    String getName();
}
