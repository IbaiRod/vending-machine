package com.vendingMachine.model.repository;

import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
}
