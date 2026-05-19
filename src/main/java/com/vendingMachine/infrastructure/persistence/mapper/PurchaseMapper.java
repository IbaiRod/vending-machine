package com.vendingMachine.infrastructure.persistence.mapper;

import com.vendingMachine.domain.model.Purchase;
import com.vendingMachine.infrastructure.persistence.entity.PurchaseEntity;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

    public Purchase toDomain(PurchaseEntity entity) {
        return new Purchase(entity.getId(), entity.getAmount());
    }

    public PurchaseEntity toEntity(Purchase domain) {
        return PurchaseEntity.builder()
                .id(domain.getId())
                .amount(domain.getAmount())
                .build();
    }
}
