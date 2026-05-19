package com.vendingMachine.application.port.input;

import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.dto.PurchaseResponse;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.domain.model.Purchase;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineUseCase {

    List<Product> showProducts();

    Purchase insertMoney(PurchaseRequest request);

    PurchaseResponse buyProduct(Integer productId, Integer purchaseId);

    Purchase findPurchaseById(Integer purchaseId);

    BigDecimal refundPurchase(Integer purchaseId);
}
