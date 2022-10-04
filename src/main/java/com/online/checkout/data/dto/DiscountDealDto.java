package com.online.checkout.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "discount_deal")
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDealDto {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "discount_code", nullable = false)
    private String discountCode;

    @Column(name = "apply_alone", nullable = false)
    private boolean applyAlone;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDto product;
}
