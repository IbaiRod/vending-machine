package com.vendingMachine.service;

import com.vendingMachine.model.dto.PurchaseRequest;
import com.vendingMachine.model.dto.PurchaseResponse;
import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;

import java.util.List;

public interface VendingMachineService {
    
    List<Product> getProductList();
    
    Purchase sumCoins(PurchaseRequest purchaseRequest);

    PurchaseResponse buyProccess(Integer idProduct, Integer idPurchase);

    Purchase getPurchaseById(Integer purchaseId);

    Double getPurchaseRefund(Integer purchaseId);
}
