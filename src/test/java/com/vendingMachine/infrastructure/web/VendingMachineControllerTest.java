package com.vendingMachine.infrastructure.web;

import com.vendingMachine.Utils;
import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.dto.PurchaseResponse;
import com.vendingMachine.application.port.input.VendingMachineUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendingMachineControllerTest {

    @InjectMocks
    VendingMachineController vendingMachineController;

    @Mock
    VendingMachineUseCase vendingMachineUseCase;

    @Test
    void itShouldReturnList() {
        when(vendingMachineUseCase.showProducts()).thenReturn(Utils.getProductList());

        assertThat(vendingMachineController.showProducts())
                .hasSize(2);
    }

    @Test
    void itShouldReturnAPurchase() {
        var purchase = Utils.getPurchase();
        when(vendingMachineUseCase.insertMoney(any(PurchaseRequest.class)))
                .thenReturn(purchase);

        var purchaseRequest = Utils.getPurchaseRequest();
        assertThat(vendingMachineController.insertMoney(purchaseRequest))
                .isEqualTo(purchase);
    }

    @Test
    void itShouldReturnProduct() {
        var purchaseResponse = Utils.getPurchaseResponse();
        when(vendingMachineUseCase.buyProduct(anyInt(), anyInt()))
                .thenReturn(purchaseResponse);

        assertThat(vendingMachineController.buyProduct(1, 1))
                .isEqualTo(purchaseResponse);
    }
}
