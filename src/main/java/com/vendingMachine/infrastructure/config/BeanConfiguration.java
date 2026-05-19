package com.vendingMachine.infrastructure.config;

import com.vendingMachine.application.port.input.VendingMachineUseCase;
import com.vendingMachine.application.port.output.ProductRepositoryPort;
import com.vendingMachine.application.port.output.PurchaseRepositoryPort;
import com.vendingMachine.application.service.VendingMachineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public VendingMachineUseCase vendingMachineUseCase(
            ProductRepositoryPort productRepository,
            PurchaseRepositoryPort purchaseRepository
    ) {
        return new VendingMachineService(productRepository, purchaseRepository);
    }
}
