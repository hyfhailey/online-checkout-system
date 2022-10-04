package com.online.checkout.business.discount.strategy;

import com.online.checkout.data.enu.DiscountType;
import com.online.checkout.data.model.DiscountCalculation;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class PercentageDiscountStrategy implements DiscountStrategy {
    private BigDecimal discount;
    private boolean applyAloneOnly;

    @Override
    public DiscountCalculation applyTo(DiscountCalculation discountCalculation) {
        if (applyAloneOnly && !discountCalculation.getAppliedDiscountStrategies().isEmpty()) {
            return discountCalculation;
        }

        BigDecimal discountedAmount = discountCalculation.getAmountAfterDiscount().multiply(discount);
        BigDecimal deduction = discountCalculation.getAmountAfterDiscount().subtract(discountedAmount);

        discountCalculation.setAmountAfterDiscount(discountedAmount);
        discountCalculation.setDiscountsApplied(discountCalculation.getDiscountsApplied().add(deduction));
        discountCalculation.getAppliedDiscountStrategies().add(this);
        return discountCalculation;
    }

    @Override
    public boolean applyAloneOnly() {
        return applyAloneOnly;
    }

    @Override
    public DiscountType getType() {
        return DiscountType.PERCENT_OFF;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append(discount.toString());
        sb.append("%");
        sb.append(" OFF");
        return sb.toString();
    }
}
