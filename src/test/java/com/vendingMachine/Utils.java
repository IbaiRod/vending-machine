package com.vendingMachine;

import com.vendingMachine.model.dto.PurchaseRequest;
import com.vendingMachine.model.dto.PurchaseResponse;
import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;

import java.util.List;

public class Utils {

    public static List<Product> getProductList() {
        return List.of(getProduct(), getProduct());
    }

    public static Product getProduct() {
        return Product.builder().build();
    }

    public static Purchase getPurchase() {
        return  Purchase.builder().build();
    }

    public static PurchaseRequest getPurchaseRequest() {
        return PurchaseRequest.builder().build();
    }

    public static PurchaseResponse getPurchaseResponse() {
        return PurchaseResponse.builder().build();
    }
}
