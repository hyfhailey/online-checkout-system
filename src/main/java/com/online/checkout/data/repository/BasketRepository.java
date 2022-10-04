package com.online.checkout.data.repository;

import com.online.checkout.data.dto.BasketDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<BasketDto, UUID> {
    Optional<BasketDto> findByIdAndCustomerId(UUID basketId, UUID customerId);
}
