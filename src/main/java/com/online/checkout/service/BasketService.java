package com.online.checkout.service;

import com.online.checkout.controller.response.BasketItemsReceiptResponse;
import com.online.checkout.data.dto.BasketDto;
import com.online.checkout.data.dto.BasketItemsDto;
import com.online.checkout.data.dto.ProductDto;
import com.online.checkout.data.model.Purchase;
import com.online.checkout.data.repository.BasketItemsRepository;
import com.online.checkout.data.repository.BasketRepository;
import com.online.checkout.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BasketService {

    @Autowired
    private BasketItemsRepository basketItemsRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountService discountService;

    public BasketItemsReceiptResponse calculateReceipt(UUID basketId, UUID customerId) {
        basketRepository.findByIdAndCustomerId(basketId, customerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Basket %s is not found for customer %s".formatted(basketId.toString(), customerId.toString())));

        List<BasketItemsDto> basketItems = basketItemsRepository.findAllByBasket_Id(basketId);

        Set<Purchase> purchases = new HashSet<>();
        for (BasketItemsDto item : basketItems) {
            purchases.add(discountService.calculateAfterDiscountPrice(item));
        }

//        Set<Purchase> purchases = basketItemsRepository.findAllByBasket_Id(basketId).stream()
//                .map(basketItemsDto -> discountService.calculateAfterDiscountPrice(basketItemsDto))
//                .collect(Collectors.toSet());
        return BasketItemsReceiptResponse.from(purchases);
    }

    public void removeProductFromBasket(UUID customerId, UUID basketId, UUID basketItemId) {
        basketRepository.findByIdAndCustomerId(basketId, customerId)
                .orElseThrow(() -> new IllegalArgumentException("Basket for this customer is not found"));

        basketItemsRepository.findByIdAndBasket_Id(basketItemId, basketId)
                .orElseThrow(() -> new IllegalArgumentException("Basket item is not found"));
        basketItemsRepository.deleteById(basketItemId);
    }

    public void addProductToBasket(UUID customerId, UUID basketId, Integer productId, long quantity) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product to be added to basket is not found."));
        basketRepository.findByIdAndCustomerId(basketId, customerId)
                .ifPresentOrElse(
                        // If basket is created for customer already
                        basket -> {
                            Optional<BasketItemsDto> basketItemsDtoOptional = basketItemsRepository
                                    .findAllByBasket_IdAndProduct_Id(basketId, productId);

                            if (basketItemsDtoOptional.isEmpty()) {
                                addProductToBasket(basket, productId, quantity);
                            } else {
                                BasketItemsDto basketItemsDto = basketItemsDtoOptional.get();
                                basketItemsDto.setQuantity(basketItemsDto.getQuantity() + quantity);
                                basketItemsRepository.save(basketItemsDto);
                            }
                        },
                        // Customer doesn't have an existing basket, need to create one
                        () -> {
                            BasketDto basketDto = BasketDto.builder().customerId(customerId).build();
                            basketRepository.save(basketDto);
                            addProductToBasket(basketDto, productId, quantity);
                        });
    }

    private void addProductToBasket(BasketDto basketDto, Integer productId, long quantity) {
        BasketItemsDto basketItem = BasketItemsDto.builder()
                .basket(basketDto)
                .product(ProductDto.builder().id(productId).build())
                .quantity(quantity)
                .build();
        basketItemsRepository.save(basketItem);
    }
}
