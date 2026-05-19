package com.vendingMachine.application.service;

import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.port.output.ProductRepositoryPort;
import com.vendingMachine.application.port.output.PurchaseRepositoryPort;
import com.vendingMachine.domain.exception.InsufficientFundsException;
import com.vendingMachine.domain.exception.OutOfStockException;
import com.vendingMachine.domain.exception.ProductNotFoundException;
import com.vendingMachine.domain.exception.PurchaseNotFoundException;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.domain.model.Purchase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendingMachineServiceTest {

    @InjectMocks
    VendingMachineService vendingMachineService;

    @Mock
    ProductRepositoryPort productRepository;
    @Mock
    PurchaseRepositoryPort purchaseRepository;

    @Test
    void showProducts_ShouldReturnAllProducts() {
        var expectedProducts = List.of(
                new Product(1L, "Water", new BigDecimal("0.45"), 2),
                new Product(2L, "Coke", new BigDecimal("1.95"), 10)
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        var result = vendingMachineService.showProducts();

        assertThat(result).isEqualTo(expectedProducts);
        verify(productRepository).findAll();
    }

    @Test
    void insertMoney_ShouldCalculateTotalAndSavePurchase() {
        var request = new PurchaseRequest();
        request.setListCoins(List.of(
                new BigDecimal("1.0"),
                new BigDecimal("2.0"),
                new BigDecimal("0.5")
        ));

        var savedPurchase = new Purchase(1L, new BigDecimal("3.5"));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(savedPurchase);

        var result = vendingMachineService.insertMoney(request);

        assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("3.5"));
        verify(purchaseRepository).save(any(Purchase.class));
    }

    @Test
    void buyProduct_ShouldSucceedWithValidPurchase() {
        var purchase = new Purchase(1L, new BigDecimal("10.0"));
        var product = new Product(1L, "Coke", new BigDecimal("5.0"), 10);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var result = vendingMachineService.buyProduct(1, 1);

        assertThat(result.getUserAmountLeft()).isEqualByComparingTo(new BigDecimal("5.0"));
        assertThat(result.getProduct().getQuantity()).isEqualTo(9);
    }

    @Test
    void buyProduct_ShouldFailWhenInsufficientFunds() {
        var purchase = new Purchase(1L, new BigDecimal("2.0"));
        var product = new Product(1L, "Coke", new BigDecimal("5.0"), 10);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientFundsException.class,
                () -> vendingMachineService.buyProduct(1, 1));
    }

    @Test
    void buyProduct_ShouldFailWhenOutOfStock() {
        var purchase = new Purchase(1L, new BigDecimal("10.0"));
        var product = new Product(1L, "Coke", new BigDecimal("5.0"), 0);

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(OutOfStockException.class,
                () -> vendingMachineService.buyProduct(1, 1));
    }

    @Test
    void buyProduct_ShouldFailWhenPurchaseNotFound() {
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PurchaseNotFoundException.class,
                () -> vendingMachineService.buyProduct(1, 1));
    }

    @Test
    void findPurchaseById_WhenPurchaseExists_ShouldReturnPurchase() {
        var expectedPurchase = new Purchase(1L, new BigDecimal("10.0"));

        when(purchaseRepository.findById(1L))
                .thenReturn(Optional.of(expectedPurchase));

        var actualPurchase = vendingMachineService.findPurchaseById(1);

        assertEquals(expectedPurchase.getId(), actualPurchase.getId());
        assertEquals(expectedPurchase.getAmount(), actualPurchase.getAmount());
        verify(purchaseRepository).findById(1L);
    }

    @Test
    void findPurchaseById_WhenPurchaseDoesNotExist_ShouldThrowPurchaseNotFoundException() {
        when(purchaseRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PurchaseNotFoundException.class,
                () -> vendingMachineService.findPurchaseById(1));
        verify(purchaseRepository).findById(1L);
    }
}
