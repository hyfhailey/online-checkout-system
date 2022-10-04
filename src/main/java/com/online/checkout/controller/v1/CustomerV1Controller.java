package com.online.checkout.controller.v1;

import com.online.checkout.controller.request.AddToBasketRequest;
import com.online.checkout.controller.request.RemoveFromBasketRequest;
import com.online.checkout.controller.response.BasketItemsReceiptResponse;
import com.online.checkout.controller.validation.ExistingBasket;
import com.online.checkout.controller.validation.ExistingCustomer;
import com.online.checkout.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Validated
public class CustomerV1Controller {

    @Autowired
    private BasketService basketService;

    @PostMapping("/v1/basket")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToBasket(@Valid @RequestBody AddToBasketRequest addToBasketRequest) {
        basketService.addProductToBasket(
                addToBasketRequest.getCustomerId(),
                addToBasketRequest.getBasketId(),
                addToBasketRequest.getProductId(),
                addToBasketRequest.getQuantity()
        );
    }

    @DeleteMapping("/v1/basket")
    @ResponseStatus(HttpStatus.OK)
    public void removeProductFromBasket(@Valid @RequestBody RemoveFromBasketRequest removeFromBasketRequest) {
        basketService.removeProductFromBasket(
                removeFromBasketRequest.getCustomerId(),
                removeFromBasketRequest.getBasketId(),
                removeFromBasketRequest.getBasketItemId())
        ;
    }

    @GetMapping("/v1/receipts/{customerId}/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    public BasketItemsReceiptResponse getReceipt(@PathVariable @ExistingCustomer UUID customerId, @PathVariable @ExistingBasket UUID basketId) {
        return basketService.calculateReceipt(basketId, customerId);
    }
}
