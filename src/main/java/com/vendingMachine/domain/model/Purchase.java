package com.vendingMachine.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase {

    private final Long id;
    private BigDecimal amount;

    public Purchase(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void deduct(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Purchase{id=" + id + ", amount=" + amount + '}';
    }
}
