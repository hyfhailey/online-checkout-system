package com.online.checkout.controller.response;

import com.online.checkout.data.model.Purchase;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
public class BasketItemsReceiptResponse {
    @NonNull
    private Set<Purchase> purchases;
    @NonNull
    private BigDecimal totalDiscountApplied;
    @NonNull
    private BigDecimal totalPrice;

    public static BasketItemsReceiptResponse from(Set<Purchase> purchases) {
        BigDecimal totalDiscountApplied = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Purchase purchase : purchases) {
            totalDiscountApplied = totalDiscountApplied.add(purchase.getDiscountApplied());
            totalPrice = totalPrice.add(purchase.getTotalPrice());
        }
        return BasketItemsReceiptResponse.builder()
                .purchases(purchases)
                .totalDiscountApplied(totalDiscountApplied)
                .totalPrice(totalPrice)
                .build();
    }
}
