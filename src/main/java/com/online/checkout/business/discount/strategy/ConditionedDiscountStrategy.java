package com.online.checkout.business.discount.strategy;

import com.online.checkout.data.enu.DiscountType;
import com.online.checkout.data.model.DiscountCalculation;

import java.util.function.Predicate;

// Examples: Buy one get 50% off the second; Buy one, get one free
abstract public class ConditionedDiscountStrategy implements DiscountStrategy {
    private final Predicate<Long> precondition;
    private final DiscountType discountType;
    private final boolean applyAloneOnly;
    private final String name;

    public ConditionedDiscountStrategy(Predicate<Long> precondition, boolean applyAloneOnly, DiscountType discountType, String name) {
        this.precondition = precondition;
        this.applyAloneOnly = applyAloneOnly;
        this.discountType = discountType;
        this.name = name;
    }

    @Override
    public DiscountCalculation applyTo(DiscountCalculation discountCalculation) {
        if (applyAloneOnly && !discountCalculation.getAppliedDiscountStrategies().isEmpty()) {
            return discountCalculation;
        }

        if (precondition.test(discountCalculation.getQuantity())) {
            this.applyConditionedDiscount(discountCalculation);
        }

        return discountCalculation;
    }

    @Override
    public boolean applyAloneOnly() {
        return this.applyAloneOnly;
    }

    @Override
    public DiscountType getType() {
        return discountType;
    }

    @Override
    public String getName() {
        return name;
    }

    abstract public DiscountCalculation applyConditionedDiscount(DiscountCalculation discountCalculation);
}
