package com.online.checkout.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Builder
@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private BigDecimal price;

    private String description;

    @OneToMany(mappedBy = "product")
    private Set<DiscountDealDto> discountDeals;
}
