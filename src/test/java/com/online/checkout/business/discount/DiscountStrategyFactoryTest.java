package com.online.checkout.business.discount;

import com.online.checkout.business.discount.strategy.DiscountStrategy;
import com.online.checkout.data.enu.DiscountType;
import org.junit.jupiter.api.Test;

import static com.online.checkout.util.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountStrategyFactoryTest {

    private final DiscountStrategyFactory discountStrategyFactory = new DiscountStrategyFactory();

    @Test
    public void testGetStrategy() {
        DiscountStrategy discountStrategy = discountStrategyFactory.getStrategy(PERCENT_DISCOUNT_CODE, true);

        assertEquals(DiscountType.PERCENT_OFF, discountStrategy.getType());
        assertTrue(discountStrategy.applyAloneOnly());

        discountStrategy = discountStrategyFactory.getStrategy(FULL_FREE_ITEM_DISCOUNT_CODE, false);

        assertEquals(DiscountType.BUY_NUM_GET_NUM_FREE, discountStrategy.getType());
        assertFalse(discountStrategy.applyAloneOnly());

        discountStrategy = discountStrategyFactory.getStrategy(PARTIAL_FREE_ITEM_DISCOUNT_CODE, true);

        assertEquals(DiscountType.BUY_NUM_GET_PERCENT_OFF_NUM, discountStrategy.getType());
        assertTrue(discountStrategy.applyAloneOnly());
    }
}
