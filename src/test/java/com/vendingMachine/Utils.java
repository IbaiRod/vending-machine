package com.vendingMachine;

import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.dto.PurchaseResponse;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.domain.model.Purchase;

import java.math.BigDecimal;
import java.util.List;

public class Utils {

    public static List<Product> getProductList() {
        return List.of(getProduct(), getProduct());
    }

    public static Product getProduct() {
        return new Product(1L, "Coke", new BigDecimal("5.0"), 10);
    }

    public static Purchase getPurchase() {
        return new Purchase(1L, new BigDecimal("10.0"));
    }

    public static PurchaseRequest getPurchaseRequest() {
        return new PurchaseRequest();
    }

    public static PurchaseResponse getPurchaseResponse() {
        return new PurchaseResponse(getProduct(), BigDecimal.ZERO);
    }
}
