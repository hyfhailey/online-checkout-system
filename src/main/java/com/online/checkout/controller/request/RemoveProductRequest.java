package com.online.checkout.controller.request;

import com.online.checkout.controller.validation.ExistingProduct;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RemoveProductRequest {
    @NotNull
    @ExistingProduct
    private Integer productId;
}
