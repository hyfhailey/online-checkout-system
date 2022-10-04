package com.online.checkout.business.discount;

import com.online.checkout.business.discount.strategy.DiscountStrategy;
import com.online.checkout.data.model.DiscountCalculation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DiscountCalculator {

    public DiscountCalculation calculate(Set<DiscountStrategy> discountStrategies, BigDecimal marketPrice, long quantity) {
        DiscountCalculation discountCalculation = initialiseCalculation(marketPrice, quantity);
        List<DiscountStrategy> sortedStrategies = new ArrayList<>(discountStrategies);
        sortedStrategies.sort(Comparator.comparing(DiscountStrategy::getType));
        for (DiscountStrategy discountStrategy : sortedStrategies) {
            discountCalculation = this.apply(discountStrategy, discountCalculation);
        }
        return discountCalculation;
    }

    private DiscountCalculation apply(DiscountStrategy discountStrategy, DiscountCalculation discountCalculation) {
        return discountStrategy.applyTo(discountCalculation);
    }

    private DiscountCalculation initialiseCalculation(BigDecimal marketPrice, long quantity) {
        return DiscountCalculation.builder()
                .marketPrice(marketPrice)
                .quantity(quantity)
                .amountAfterDiscount(marketPrice.multiply(new BigDecimal(quantity)))
                .appliedDiscountStrategies(new HashSet<>())
                .discountsApplied(BigDecimal.ZERO)
                .build();
    }
}

