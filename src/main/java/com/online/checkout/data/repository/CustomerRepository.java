package com.online.checkout.data.repository;

import com.online.checkout.data.dto.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerDto, UUID> {
}
