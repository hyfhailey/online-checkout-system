package com.online.checkout.service;

import com.online.checkout.business.discount.DiscountCalculator;
import com.online.checkout.business.discount.DiscountStrategyFactory;
import com.online.checkout.data.dto.BasketItemsDto;
import com.online.checkout.data.dto.DiscountDealDto;
import com.online.checkout.data.repository.DiscountDealRepository;
import com.online.checkout.data.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.online.checkout.util.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    DiscountDealRepository discountDealRepository;

    @Mock
    DiscountCalculator discountCalculator;

    @Mock
    DiscountStrategyFactory discountStrategyFactory;

    @InjectMocks
    DiscountService discountService;

    @Captor
    private ArgumentCaptor<DiscountDealDto> discountDealCaptor;

    @Test
    public void testAddNewDiscountDeal() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));

        discountService.addNewDiscountDeal(PERCENT_DISCOUNT_CODE, true, PRODUCT_ID);

        verify(discountDealRepository).save(discountDealCaptor.capture());

        DiscountDealDto discountDealDto = discountDealCaptor.getValue();

        assertEquals(PERCENT_DISCOUNT_CODE, discountDealDto.getDiscountCode());
    }

    @Test
    public void testAddNewDiscountDealForNonExistentProduct() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> discountService.addNewDiscountDeal(PERCENT_DISCOUNT_CODE, true, PRODUCT_ID));
    }
}
