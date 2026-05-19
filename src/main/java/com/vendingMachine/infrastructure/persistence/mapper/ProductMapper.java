package com.vendingMachine.infrastructure.persistence.mapper;

import com.vendingMachine.domain.model.Product;
import com.vendingMachine.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity() != null ? entity.getQuantity() : 0
        );
    }

    public ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .quantity(domain.getQuantity())
                .build();
    }
}
