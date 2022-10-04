package com.online.checkout.data.model;

import com.online.checkout.business.discount.strategy.DiscountStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCalculation {
    long quantity;
    BigDecimal marketPrice;
    BigDecimal amountAfterDiscount;
    BigDecimal discountsApplied;
    Set<DiscountStrategy> appliedDiscountStrategies;
}
