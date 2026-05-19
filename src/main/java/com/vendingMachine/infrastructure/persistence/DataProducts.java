package com.vendingMachine.infrastructure.persistence;

import com.vendingMachine.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class DataProducts {

    public static List<Product> getProducts() {
        return List.of(
                new Product(null, "Water", new BigDecimal("0.45"), 2),
                new Product(null, "Coke", new BigDecimal("1.95"), 10),
                new Product(null, "Clipper", new BigDecimal("1.50"), 10),
                new Product(null, "Fanta", new BigDecimal("1.50"), 10),
                new Product(null, "Beer", new BigDecimal("1.50"), 10),
                new Product(null, "0.0 Beer", new BigDecimal("1.50"), 10),
                new Product(null, "Coffee", new BigDecimal("1.50"), 10),
                new Product(null, "Tea", new BigDecimal("1.50"), 10)
        );
    }
}
