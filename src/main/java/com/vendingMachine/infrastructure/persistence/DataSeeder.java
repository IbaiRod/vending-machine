package com.vendingMachine.infrastructure.persistence;

import com.vendingMachine.application.port.output.ProductRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepositoryPort productRepository;

    public DataSeeder(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            DataProducts.getProducts().forEach(productRepository::save);
        }
    }
}
