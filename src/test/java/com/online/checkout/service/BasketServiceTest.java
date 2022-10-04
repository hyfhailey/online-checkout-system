package com.online.checkout.service;

import com.online.checkout.controller.response.BasketItemsReceiptResponse;
import com.online.checkout.data.dto.BasketItemsDto;
import com.online.checkout.data.model.Purchase;
import com.online.checkout.data.repository.BasketItemsRepository;
import com.online.checkout.data.repository.BasketRepository;
import com.online.checkout.data.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.online.checkout.util.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    BasketRepository basketRepository;

    @Mock
    BasketItemsRepository basketItemsRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    DiscountService discountService;

    @InjectMocks
    BasketService basketService;

    @Captor
    private ArgumentCaptor<BasketItemsDto> basketItemCaptor;


    @Test
    public void testCalculateReceipt() {
        when(basketRepository.findByIdAndCustomerId(any(), any())).thenReturn(Optional.of(BASKET));
        when(basketItemsRepository.findAllByBasket_Id(any())).thenReturn(List.of(BASKET_ITEM));
        when(discountService.calculateAfterDiscountPrice(any())).thenReturn(PURCHASE);

        BasketItemsReceiptResponse res = basketService.calculateReceipt(BASKET_ID, CUSTOMER_ID);

        assertTrue(res.getPurchases().contains(PURCHASE));
        assertEquals(1, res.getPurchases().size());
        assertEquals(BigDecimal.ZERO, res.getTotalDiscountApplied());
        assertEquals(BASKET_TOTAL_PRICE, res.getTotalPrice());
    }

    @Test
    public void testCalculateReceiptForNonExistentBasket() {
        when(basketRepository.findByIdAndCustomerId(any(), any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> basketService.calculateReceipt(BASKET_ID, CUSTOMER_ID));
    }

    @Test
    public void testRemoveFromNonExistentBasket() {
        when(basketRepository.findByIdAndCustomerId(any(), any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> basketService.removeProductFromBasket(CUSTOMER_ID, BASKET_ID, BASKET_ITEM_ID));
    }

    @Test
    public void testRemoveNonExistentItemFromBasket() {
        when(basketRepository.findByIdAndCustomerId(any(), any())).thenReturn(Optional.of(BASKET));
        when(basketItemsRepository.findByIdAndBasket_Id(any(), any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> basketService.removeProductFromBasket(CUSTOMER_ID, BASKET_ID, BASKET_ITEM_ID));
    }

    @Test
    public void testAddNonExistentProductToBasket() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> basketService.addProductToBasket(CUSTOMER_ID, BASKET_ID, PRODUCT_ID, BASKET_QUANTITY));
    }

    @Test
    public void testAddToExistingBasket() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));
        when(basketRepository.findByIdAndCustomerId(any(), any())).thenReturn(Optional.of(BASKET));
        when(basketItemsRepository.findAllByBasket_IdAndProduct_Id(any(), any())).thenReturn(Optional.of(BASKET_ITEM));

       basketService.addProductToBasket(CUSTOMER_ID, BASKET_ID, PRODUCT_ID, BASKET_QUANTITY);
       verify(basketItemsRepository).save(basketItemCaptor.capture());

       BasketItemsDto basketItemsDto = basketItemCaptor.getValue();
       assertEquals(BASKET_QUANTITY * 2, basketItemsDto.getQuantity());
       assertEquals(BASKET_ID, basketItemsDto.getBasket().getId());
    }
}
