package com.vendingMachine.service.impl;

import com.vendingMachine.Utils;
import com.vendingMachine.exception.EntityNotFoundException;
import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;
import com.vendingMachine.model.repository.ProductRepository;
import com.vendingMachine.model.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class VendingMachineServiceImplTest {

    @InjectMocks
    VendingMachineServiceImpl vendingMachineService;

    @Mock
    ProductRepository productRepository;
    @Mock
    PurchaseRepository purchaseRepository;

    @Test
    void getProductList_ShouldReturnAllProducts() {
        List<Product> expectedProducts = Utils.getProductList();
        when(productRepository.findAll()).thenReturn(expectedProducts);
        
        List<Product> result = vendingMachineService.getProductList();

        assertThat(result).isEqualTo(expectedProducts);
        verify(productRepository).findAll();
    }

    @Test
    void sumCoins_ShouldCalculateTotalAndSavePurchase() {
        var purchaseRequest = Utils.getPurchaseRequest();
        purchaseRequest.setListCoins(List.of(1.0, 2.0, 0.5));

        var expectedPurchase = Purchase.builder()
                .amount(3.5)
                .build();
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(expectedPurchase);
        
        Purchase result = vendingMachineService.sumCoins(purchaseRequest);

        assertThat(result.getAmount()).isEqualTo(3.5);
        verify(purchaseRepository).save(any(Purchase.class));
    }

    @Test
    void buyProcess_ShouldSucceedWithValidPurchase() {
        var purchase = Purchase.builder()
                .id(1L)
                .amount(10.0)
                .build();

        var product = Product.builder()
                .id(1L)
                .price(5.0)
                .quantity(10)
                .build();

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var result = vendingMachineService.buyProccess(1, 1);
        
        assertThat(result.getUserAmountLeft()).isEqualTo(5.0);
        assertThat(result.getProduct().getQuantity()).isEqualTo(9);
    }

    @Test
    void buyProcess_ShouldFailWhenInsufficientFunds() {
        var purchase = Purchase.builder()
                .id(1L)
                .amount(2.0)
                .build();

        var product = Product.builder()
                .id(1L)
                .price(5.0)
                .quantity(10)
                .build();

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(EntityNotFoundException.class,
                () -> vendingMachineService.buyProccess(1, 1));
    }

    @Test
    void buyProcess_ShouldFailWhenOutOfStock() {
        var purchase = Purchase.builder()
                .id(1L)
                .amount(10.0)
                .build();

        var product = Product.builder()
                .id(1L)
                .price(5.0)
                .quantity(0)
                .build();

        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(purchase));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(EntityNotFoundException.class,
                () -> vendingMachineService.buyProccess(1, 1));
    }

    @Test
    void buyProcess_ShouldFailWhenPurchaseNotFound() {
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> vendingMachineService.buyProccess(1, 1));
    }

    @Test
    void getPurchaseById_WhenPurchaseExists_ShouldReturnPurchase() {
        Purchase expectedPurchase = Purchase.builder()
                .id(1L)
                .amount(10.0)
                .build();
        
        when(purchaseRepository.findById(1L))
                .thenReturn(Optional.of(expectedPurchase));

        Purchase actualPurchase = vendingMachineService.getPurchaseById(1);

        assertEquals(expectedPurchase.getId(), actualPurchase.getId());
        assertEquals(expectedPurchase.getAmount(), actualPurchase.getAmount());
        verify(purchaseRepository).findById(1L);
    }

    @Test
    void getPurchaseById_WhenPurchaseDoesNotExist_ShouldThrowEntityNotFoundException() {
        when(purchaseRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
                () -> vendingMachineService.getPurchaseById(1));
        verify(purchaseRepository).findById(1L);
    }
}