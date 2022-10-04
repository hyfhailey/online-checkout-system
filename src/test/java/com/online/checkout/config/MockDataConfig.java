package com.online.checkout.config;

import com.online.checkout.data.repository.*;
import com.online.checkout.service.ProductService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockDataConfig {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private BasketRepository basketRepository;

    @MockBean
    private BasketItemsRepository basketItemsRepository;

    @MockBean
    private DiscountDealRepository discountDealRepository;

    @MockBean
    private CustomerRepository customerRepository;
}
