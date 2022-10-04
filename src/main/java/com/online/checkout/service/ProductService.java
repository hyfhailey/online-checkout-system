package com.online.checkout.service;

import com.online.checkout.data.dto.ProductDto;
import com.online.checkout.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createNewProduct(String name, String description, BigDecimal price) {
        productRepository.findByNameAndDescriptionAndPrice(name, description, price)
                .ifPresent(s -> {throw new IllegalArgumentException("Such product exists already.");});
        ProductDto productDto = ProductDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
        productRepository.save(productDto);
    }

    public void removeProduct(Integer productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product to be deleted is not found."));
        productRepository.deleteById(productId);
    }
}
