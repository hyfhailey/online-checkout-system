package com.online.checkout.util;

import com.online.checkout.data.dto.BasketDto;
import com.online.checkout.data.dto.BasketItemsDto;
import com.online.checkout.data.dto.ProductDto;
import com.online.checkout.data.model.Purchase;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class TestObjects {
    public static final String PERCENT_DISCOUNT_CODE = "50% OFF";
    public static final String FULL_FREE_ITEM_DISCOUNT_CODE = "BUY 1 GET 1 FREE";
    public static final String PARTIAL_FREE_ITEM_DISCOUNT_CODE = "BUY 1 GET 50% OFF THE SECOND";

    public static final Integer PRODUCT_ID = 1;
    public static final String PRODUCT_NAME = "test-product";
    public static final String PRODUCT_DESCRIPTION = "test-description";
    public static final BigDecimal PRICE = BigDecimal.TEN;
    public static final ProductDto PRODUCT = ProductDto.builder()
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESCRIPTION)
            .price(PRICE)
            .id(PRODUCT_ID)
            .build();

    public static final UUID CUSTOMER_ID = UUID.randomUUID();
    public static final UUID BASKET_ID = UUID.randomUUID();
    public static final UUID BASKET_ITEM_ID = UUID.randomUUID();
    public static final BasketDto BASKET = BasketDto.builder()
            .id(BASKET_ID)
            .customerId(CUSTOMER_ID)
            .build();
    public static final long BASKET_QUANTITY = 20L;
    public static final BigDecimal BASKET_TOTAL_PRICE = PRICE.multiply(new BigDecimal(BASKET_QUANTITY));
    public static final BasketItemsDto BASKET_ITEM = BasketItemsDto.builder()
            .id(BASKET_ITEM_ID)
            .basket(BASKET)
            .product(PRODUCT)
            .quantity(BASKET_QUANTITY)
            .build();

    public static final Purchase PURCHASE = Purchase.builder()
            .productId(PRODUCT_ID)
            .productName(PRODUCT_NAME)
            .quantity(BASKET_QUANTITY)
            .appliedDiscountDeals(Set.of())
            .discountApplied(BigDecimal.ZERO)
            .totalPrice(BASKET_TOTAL_PRICE)
            .build();
}
