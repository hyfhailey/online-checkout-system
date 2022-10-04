package com.online.checkout.controller.v1;

import com.online.checkout.controller.request.NewDiscountDealRequest;
import com.online.checkout.controller.request.NewProductRequest;
import com.online.checkout.controller.request.RemoveProductRequest;
import com.online.checkout.service.DiscountService;
import com.online.checkout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class AdminV1Controller {
    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountService discountService;

    @PostMapping("/v1/admin/product")
    @ResponseStatus(HttpStatus.OK)
    public void createNewProduct(@Valid @RequestBody NewProductRequest newProductRequest) {
        productService.createNewProduct(
                newProductRequest.getName(),
                newProductRequest.getDescription(),
                newProductRequest.getPrice()
        );
    }

    @DeleteMapping("/v1/admin/product")
    @ResponseStatus(HttpStatus.OK)
    public void removeProduct(@Valid @RequestBody RemoveProductRequest removeProductRequest) {
        productService.removeProduct(removeProductRequest.getProductId());
    }

    @PostMapping("/v1/admin/product-discounts")
    @ResponseStatus(HttpStatus.OK)
    public void addNewDiscounts(@Valid @RequestBody NewDiscountDealRequest newDiscountDealRequest) {
        String discountCode = newDiscountDealRequest.getDiscountCode();
        boolean applyAlone = newDiscountDealRequest.isApplyAlone();
        Integer productId = newDiscountDealRequest.getProductId();
        discountService.addNewDiscountDeal(discountCode, applyAlone, productId);
    }
}
