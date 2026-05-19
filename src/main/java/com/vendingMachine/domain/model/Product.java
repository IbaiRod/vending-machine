package com.vendingMachine.domain.model;

import com.vendingMachine.domain.exception.OutOfStockException;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;
    private int quantity;

    public Product(Long id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean hasStock() {
        return quantity > 0;
    }

    public boolean isAffordableWith(BigDecimal amount) {
        return amount.compareTo(price) >= 0;
    }

    public void decreaseStock() {
        if (!hasStock()) {
            throw new OutOfStockException();
        }
        quantity--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", quantity=" + quantity + '}';
    }
}
