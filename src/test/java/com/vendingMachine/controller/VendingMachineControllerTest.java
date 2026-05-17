package com.vendingMachine.controller;

import com.vendingMachine.Utils;
import com.vendingMachine.model.dto.PurchaseRequest;
import com.vendingMachine.model.dto.PurchaseResponse;
import com.vendingMachine.model.repository.ProductRepository;
import com.vendingMachine.model.repository.PurchaseRepository;
import com.vendingMachine.service.VendingMachineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendingMachineControllerTest {

    @InjectMocks
    VendingMachineController vendingMachineController;

    @Mock
    VendingMachineService vendingMachineService;



    @Test
    void itShouldReturnList(){
        when(vendingMachineService.getProductList()).thenReturn(Utils.getProductList());

        assertThat(vendingMachineController.showProducts())
                .hasSize(2);
    }

    @Test
    void itShouldReturnAPurchase(){
        var purchase = Utils.getPurchase();
        when(vendingMachineService.sumCoins(any(PurchaseRequest.class)))
                .thenReturn(purchase);

        var purchaseRequest = Utils.getPurchaseRequest();
        assertThat(vendingMachineController.saveCoins(purchaseRequest))
                .isEqualTo(purchase);
    }

    @Test
    void itShouldReturnProduct() throws Exception {
        var purchaseResponse = Utils.getPurchaseResponse();
        when(vendingMachineService.buyProcess(anyInt(), anyInt()))
                .thenReturn(purchaseResponse);

        assertThat(vendingMachineController.buyProduct(1, 1))
                .isEqualTo(purchaseResponse);
    }


}