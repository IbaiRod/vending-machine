package com.vendingMachine.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PurchaseRequest {

    private List<Double> listCoins;

    
}
