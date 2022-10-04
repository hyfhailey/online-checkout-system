package com.online.checkout.data.repository;

import com.online.checkout.data.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductDto, Integer> {
    Optional<ProductDto> findByNameAndDescriptionAndPrice(String name, String description, BigDecimal price);
}
