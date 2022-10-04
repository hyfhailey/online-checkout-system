package com.online.checkout.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "basket")
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "customer_id", columnDefinition = "uuid")
    private UUID customerId;

    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private Set<BasketItemsDto> products;
}
