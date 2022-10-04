package com.online.checkout.service;

import com.online.checkout.business.discount.DiscountCalculator;
import com.online.checkout.business.discount.DiscountStrategyFactory;
import com.online.checkout.business.discount.strategy.DiscountStrategy;
import com.online.checkout.data.dto.BasketItemsDto;
import com.online.checkout.data.dto.DiscountDealDto;
import com.online.checkout.data.dto.ProductDto;
import com.online.checkout.data.model.DiscountCalculation;
import com.online.checkout.data.model.Purchase;
import com.online.checkout.data.repository.DiscountDealRepository;
import com.online.checkout.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DiscountService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountDealRepository discountDealRepository;

    @Autowired
    private DiscountCalculator discountCalculator;

    @Autowired
    private DiscountStrategyFactory discountStrategyFactory;

    public void addNewDiscountDeal(String discountCode, boolean applyAlone, Integer productId) {
        ProductDto productDto = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product is not found"));

        DiscountDealDto discountDealDto = DiscountDealDto.builder()
                .discountCode(discountCode)
                .applyAlone(applyAlone)
                .product(productDto)
                .build();

        discountDealRepository.save(discountDealDto);
    }

    protected Purchase calculateAfterDiscountPrice(BasketItemsDto basketItemsDto) {
        Purchase.PurchaseBuilder builder = Purchase.builder();
        ProductDto productDto = basketItemsDto.getProduct();

        builder.productId(productDto.getId());
        builder.productName(productDto.getName());
        builder.quantity(basketItemsDto.getQuantity());

        List<DiscountDealDto> eligibleDiscounts = discountDealRepository.findAllByProduct_Id(productDto.getId());

        if (eligibleDiscounts.size() > 0) {
            Set<DiscountStrategy> discountStrategies = new HashSet<>();
            for (DiscountDealDto discountDealDto : eligibleDiscounts) {
                DiscountStrategy discountStrategy = discountStrategyFactory.getStrategy(discountDealDto.getDiscountCode(),
                        discountDealDto.isApplyAlone());
                discountStrategies.add(discountStrategy);
            }

            DiscountCalculation discountCalculation = discountCalculator.calculate(
                    discountStrategies,
                    productDto.getPrice(),
                    basketItemsDto.getQuantity()
            );

            builder.totalPrice(discountCalculation.getAmountAfterDiscount());
            builder.discountApplied(discountCalculation.getDiscountsApplied());

            Set<String> appliedDiscountCodes = eligibleDiscounts.stream()
                    .map(DiscountDealDto::getDiscountCode)
                    .collect(Collectors.toSet());

            builder.appliedDiscountDeals(appliedDiscountCodes);
        }

        return builder.build();
    }
}
