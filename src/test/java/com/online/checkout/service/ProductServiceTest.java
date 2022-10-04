package com.online.checkout.service;

import com.online.checkout.data.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.online.checkout.util.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void testCreateExistingProduct() {
        when(productRepository.findByNameAndDescriptionAndPrice(any(), any(), any())).thenReturn(Optional.of(PRODUCT));

        assertThrows(IllegalArgumentException.class, () -> productService.createNewProduct(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRICE));
    }

    @Test
    public void testRemoveNonExistentProduct() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.removeProduct(1));
    }
}
