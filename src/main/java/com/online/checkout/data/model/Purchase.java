package com.online.checkout.data.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
public class Purchase {
    Integer productId;
    String productName;
    long quantity;
    BigDecimal totalPrice;
    Set<String> appliedDiscountDeals;
    BigDecimal discountApplied;
}
