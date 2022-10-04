package com.online.checkout.controller.request;

import com.online.checkout.controller.validation.ExistingDiscountCode;
import com.online.checkout.controller.validation.ExistingProduct;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewDiscountDealRequest {
    @NotNull
    @ExistingProduct
    private Integer productId;

    @NotNull
    @NotBlank
    @ExistingDiscountCode
    private String discountCode;

    @NotNull
    private boolean applyAlone;
}
