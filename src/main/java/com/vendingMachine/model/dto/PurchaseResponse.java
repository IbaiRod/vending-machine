package com.vendingMachine.model.dto;

import com.vendingMachine.model.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PurchaseResponse {

    private Product product;

    private BigDecimal userAmountLeft;
}
