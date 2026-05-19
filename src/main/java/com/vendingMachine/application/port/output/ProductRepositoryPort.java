package com.vendingMachine.application.port.output;

import com.vendingMachine.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    long count();
}
