package com.online.checkout.data.repository;

import com.online.checkout.data.dto.DiscountDealDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DiscountDealRepository extends JpaRepository<DiscountDealDto, UUID> {
    List<DiscountDealDto> findAllByProduct_Id(Integer productId);
}
