package com.online.checkout.business.discount;

import com.online.checkout.business.discount.strategy.DiscountStrategy;
import com.online.checkout.business.discount.strategy.FreeItemDiscountStrategy;
import com.online.checkout.data.enu.DiscountType;
import com.online.checkout.data.model.DiscountCalculation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountCalculatorTest {

    private final DiscountCalculator discountCalculator = new DiscountCalculator();

    private static final BigDecimal MARKET_PRICE = BigDecimal.valueOf(30L);
    private static final long QUANTITY = 2L;

    @Test
    public void testCalculate() {
        Set<DiscountStrategy> discountStrategies = new HashSet<>();
        FreeItemDiscountStrategy buyOneGetOneFree = new FreeItemDiscountStrategy(
                        s -> s >= 2,
                        true,
                        DiscountType.BUY_NUM_GET_NUM_FREE,
                        "test-product",
                        BigDecimal.ONE
        );
        discountStrategies.add(buyOneGetOneFree);

        DiscountCalculation discountCalculation = discountCalculator.calculate(discountStrategies, MARKET_PRICE, QUANTITY);
        assertEquals(BigDecimal.valueOf(30L), discountCalculation.getDiscountsApplied());
        assertEquals(BigDecimal.valueOf(30L), discountCalculation.getAmountAfterDiscount());
        assertTrue(discountCalculation.getAppliedDiscountStrategies().contains(buyOneGetOneFree));
    }

    @Test
    public void testCalculateEmptyDiscountStrategies() {
        Set<DiscountStrategy> discountStrategies = new HashSet<>();

        DiscountCalculation discountCalculation = discountCalculator.calculate(discountStrategies, MARKET_PRICE, QUANTITY);
        assertEquals(BigDecimal.ZERO, discountCalculation.getDiscountsApplied());
        assertTrue(discountCalculation.getAppliedDiscountStrategies().isEmpty());
    }
}
