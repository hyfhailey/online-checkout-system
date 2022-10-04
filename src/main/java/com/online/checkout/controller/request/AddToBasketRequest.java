package com.online.checkout.controller.request;

import com.online.checkout.controller.validation.ExistingCustomer;
import com.online.checkout.controller.validation.ExistingProduct;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddToBasketRequest {
    @NotNull
    @ExistingCustomer
    UUID customerId;

    UUID basketId;

    @NotNull
    @ExistingProduct
    Integer productId;

    @NotNull
    long quantity;
}
