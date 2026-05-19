package com.vendingMachine.infrastructure.persistence.repository;

import com.vendingMachine.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductRepository extends JpaRepository<ProductEntity, Long> {
}
