package com.online.checkout.data.repository;

import com.online.checkout.data.dto.BasketItemsDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BasketItemsRepository extends JpaRepository<BasketItemsDto, UUID> {
    List<BasketItemsDto> findAllByBasket_Id(UUID basketId);
    Optional<BasketItemsDto> findByIdAndBasket_Id(UUID id, UUID basketId);
    Optional<BasketItemsDto> findAllByBasket_IdAndProduct_Id(UUID basketId, Integer productId);
}
