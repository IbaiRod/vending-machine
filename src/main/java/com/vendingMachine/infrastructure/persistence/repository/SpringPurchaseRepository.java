package com.vendingMachine.infrastructure.persistence.repository;

import com.vendingMachine.infrastructure.persistence.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
