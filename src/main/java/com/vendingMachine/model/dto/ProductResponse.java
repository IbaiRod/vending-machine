package com.vendingMachine.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    
    private  String message;
    
}
