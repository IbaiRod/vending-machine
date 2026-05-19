package com.vendingMachine.application.service;

import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.dto.PurchaseResponse;
import com.vendingMachine.application.port.input.VendingMachineUseCase;
import com.vendingMachine.application.port.output.ProductRepositoryPort;
import com.vendingMachine.application.port.output.PurchaseRepositoryPort;
import com.vendingMachine.domain.exception.InsufficientFundsException;
import com.vendingMachine.domain.exception.OutOfStockException;
import com.vendingMachine.domain.exception.ProductNotFoundException;
import com.vendingMachine.domain.exception.PurchaseNotFoundException;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.domain.model.Purchase;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineService implements VendingMachineUseCase {

    private final ProductRepositoryPort productRepository;
    private final PurchaseRepositoryPort purchaseRepository;

    public VendingMachineService(ProductRepositoryPort productRepository, PurchaseRepositoryPort purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<Product> showProducts() {
        return productRepository.findAll();
    }

    @Override
    public Purchase insertMoney(PurchaseRequest request) {
        var total = request.getListCoins().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var purchase = new Purchase(null, total);
        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional
    public PurchaseResponse buyProduct(Integer productId, Integer purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId.longValue())
                .orElseThrow(() -> new PurchaseNotFoundException(purchaseId.longValue()));

        var product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ProductNotFoundException(productId.longValue()));

        if (!product.hasStock()) {
            throw new OutOfStockException();
        }
        if (!product.isAffordableWith(purchase.getAmount())) {
            throw new InsufficientFundsException();
        }

        product.decreaseStock();
        productRepository.save(product);

        return new PurchaseResponse(product, purchase.getAmount().subtract(product.getPrice()));
    }

    @Override
    public Purchase findPurchaseById(Integer purchaseId) {
        return purchaseRepository.findById(purchaseId.longValue())
                .orElseThrow(() -> new PurchaseNotFoundException(purchaseId.longValue()));
    }

    @Override
    public BigDecimal refundPurchase(Integer purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId.longValue())
                .orElseThrow(() -> new PurchaseNotFoundException(purchaseId.longValue()));

        var refund = purchase.getAmount();

        purchaseRepository.deleteById(purchaseId.longValue());

        return refund;
    }
}
