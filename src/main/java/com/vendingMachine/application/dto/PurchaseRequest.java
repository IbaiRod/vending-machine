package com.vendingMachine.application.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseRequest {

    private List<BigDecimal> listCoins;

    public PurchaseRequest() {
    }

    public PurchaseRequest(List<BigDecimal> listCoins) {
        this.listCoins = listCoins;
    }

    public List<BigDecimal> getListCoins() {
        return listCoins;
    }

    public void setListCoins(List<BigDecimal> listCoins) {
        this.listCoins = listCoins;
    }
}
