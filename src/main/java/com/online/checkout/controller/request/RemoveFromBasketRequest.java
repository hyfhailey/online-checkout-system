package com.online.checkout.controller.request;

import com.online.checkout.controller.validation.ExistingBasket;
import com.online.checkout.controller.validation.ExistingBasketItem;
import com.online.checkout.controller.validation.ExistingCustomer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class RemoveFromBasketRequest {
    @NotNull
    @ExistingCustomer
    UUID customerId;

    @NotNull
    @ExistingBasket
    UUID basketId;

    @NotNull
    @ExistingBasketItem
    UUID basketItemId;
}
