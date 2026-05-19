package com.vendingMachine.application.dto;

import com.vendingMachine.domain.model.Product;

import java.math.BigDecimal;

public class PurchaseResponse {

    private final Product product;
    private final BigDecimal userAmountLeft;

    public PurchaseResponse(Product product, BigDecimal userAmountLeft) {
        this.product = product;
        this.userAmountLeft = userAmountLeft;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getUserAmountLeft() {
        return userAmountLeft;
    }
}
