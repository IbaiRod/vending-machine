package com.vendingMachine.model;

import com.vendingMachine.model.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DataProducts {

    public static List<Product> getProducts() {

        return Arrays.asList(
                Product.builder().id(1L).price(new BigDecimal("0.45")).quantity(2).name("Water").build(),
                Product.builder().id(2L).price(new BigDecimal("1.95")).quantity(10).name("Coke").build(),
                Product.builder().id(3L).price(new BigDecimal("1.50")).quantity(10).name("Clipper").build(),
                Product.builder().id(4L).price(new BigDecimal("1.50")).quantity(10).name("Fanta").build(),
                Product.builder().id(5L).price(new BigDecimal("1.50")).quantity(10).name("Beer").build(),
                Product.builder().id(6L).price(new BigDecimal("1.50")).quantity(10).name("0.0 Beer").build(),
                Product.builder().id(7L).price(new BigDecimal("1.50")).quantity(10).name("Coffee").build(),
                Product.builder().id(8L).price(new BigDecimal("1.50")).quantity(10).name("Tea").build()
        );
    }

}
