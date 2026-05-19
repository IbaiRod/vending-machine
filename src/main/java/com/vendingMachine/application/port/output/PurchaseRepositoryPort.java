package com.vendingMachine.application.port.output;

import com.vendingMachine.domain.model.Purchase;

import java.util.Optional;

public interface PurchaseRepositoryPort {

    Purchase save(Purchase purchase);

    Optional<Purchase> findById(Long id);

    void deleteById(Long id);
}
