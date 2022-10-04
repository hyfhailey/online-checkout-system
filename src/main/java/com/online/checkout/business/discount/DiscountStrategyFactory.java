package com.online.checkout.business.discount;

import com.online.checkout.business.discount.strategy.DiscountStrategy;
import com.online.checkout.business.discount.strategy.FreeItemDiscountStrategy;
import com.online.checkout.business.discount.strategy.PercentageDiscountStrategy;
import com.online.checkout.data.enu.DiscountType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.regex.*;

import static com.online.checkout.util.DiscountCodeRegexes.*;

@Component
public class DiscountStrategyFactory {

    public DiscountStrategy getStrategy(String discountCode, boolean applyAloneOnly) {
        Matcher matcher = BUY_NUM_GET_NUM_FREE.matcher(discountCode);
        if (matcher.find()) {
            long firstParam = Long.parseLong(matcher.group(1));
            long secondParam = Long.parseLong(matcher.group(2));
            Predicate<Long> condition = quantity -> quantity >= (firstParam + secondParam);
            return new FreeItemDiscountStrategy(condition, applyAloneOnly, DiscountType.BUY_NUM_GET_NUM_FREE, discountCode, new BigDecimal(secondParam));
        }

        matcher = BUY_NUM_GET_PERCENT_OFF_THE_NUM.matcher(discountCode);
        if (matcher.find()) {
            long secondParam = Long.parseLong(matcher.group(2));
            long thirdParam = OrdinalNumber.valueOf(matcher.group(3)).ordinal() + 1;

            Predicate<Long> condition = quantity -> quantity >= thirdParam;
            return new FreeItemDiscountStrategy(condition, applyAloneOnly, DiscountType.BUY_NUM_GET_PERCENT_OFF_NUM, discountCode, new BigDecimal(secondParam).scaleByPowerOfTen(-2));
        }

        matcher = PERCENT_OFF.matcher(discountCode);
        if (matcher.find()) {
            long firstParam = Long.parseLong(matcher.group(1));
            return new PercentageDiscountStrategy(new BigDecimal(firstParam), applyAloneOnly);
        }

        throw new IllegalArgumentException("Invalid discount code");
    }

    private enum OrdinalNumber {
        FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, TENTH;
    }
}
