package com.online.checkout.business.discount.strategy;

import com.online.checkout.data.enu.DiscountType;
import com.online.checkout.data.model.DiscountCalculation;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class FreeItemDiscountStrategy extends ConditionedDiscountStrategy{
    private final BigDecimal numOfFreeItem;

    public FreeItemDiscountStrategy(Predicate<Long> precondition, boolean applyAloneOnly, DiscountType discountType, String name, BigDecimal numOfFreeItem) {
        super(precondition, applyAloneOnly, discountType, name);
        this.numOfFreeItem = numOfFreeItem;
    }

    @Override
    public DiscountCalculation applyConditionedDiscount(DiscountCalculation discountCalculation) {
        BigDecimal deduction = discountCalculation.getMarketPrice().multiply(numOfFreeItem);
        discountCalculation.setAmountAfterDiscount(discountCalculation.getAmountAfterDiscount().subtract(deduction));
        discountCalculation.setDiscountsApplied(discountCalculation.getDiscountsApplied().add(deduction));
        discountCalculation.getAppliedDiscountStrategies().add(this);
        return discountCalculation;
    }
}
