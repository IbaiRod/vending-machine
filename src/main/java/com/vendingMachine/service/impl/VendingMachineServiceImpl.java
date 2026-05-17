package com.vendingMachine.service.impl;

import com.vendingMachine.exception.EntityNotFoundException;
import com.vendingMachine.exception.UserExceptions;
import com.vendingMachine.model.dto.PurchaseRequest;
import com.vendingMachine.model.dto.PurchaseResponse;
import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;
import com.vendingMachine.model.repository.ProductRepository;
import com.vendingMachine.model.repository.PurchaseRepository;
import com.vendingMachine.service.VendingMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public VendingMachineServiceImpl(ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<Product> getProductList() {
        
        return productRepository.findAll();
    }

    @Override
    public Purchase sumCoins(PurchaseRequest purchaseRequest) {

        var sum = purchaseRequest.getListCoins().stream()
                .reduce(0.0, Double::sum);

        var purchase = Purchase.builder()
                .amount(sum).build();

        purchaseRepository.save(purchase);
        
        return purchase;
    }

    @Override
    @Transactional
    public PurchaseResponse buyProcess(Integer idProduct, Integer idPurchase) {
        
        var purchase = purchaseRepository.findById(Long.valueOf(idPurchase))
                .orElseThrow(() -> new EntityNotFoundException("Purchase", String.valueOf(idPurchase)));

        var product = productRepository.findById(Long.valueOf(idProduct))
                .filter(isStockAvailable().and(isGreaterAmountThanPrice(purchase)))
                .orElseThrow(() -> new UserExceptions("Product", String.valueOf(idProduct)));

        product.restQuantity();
        productRepository.save(product);
        
        return PurchaseResponse.builder()
                .product(product)
                .userAmountLeft(purchase.getAmount() - product.getPrice())
                .build();
    }

    @Override
    public Purchase getPurchaseById(Integer purchaseId) {

        return purchaseRepository.findById(Long.valueOf(purchaseId))
                .orElseThrow(() -> new EntityNotFoundException("Purchase", String.valueOf(purchaseId)));
    }

    @Override
    public Double getPurchaseRefund(Integer purchaseId) {

        var purchase = purchaseRepository.findById(Long.valueOf(purchaseId))
                .orElseThrow(() -> new EntityNotFoundException("Purchase", String.valueOf(purchaseId)));

        var refund = purchase.getAmount();

        purchaseRepository.deleteById(Long.valueOf(purchaseId));

        return refund;
    }

    private static Predicate<Product> isStockAvailable() {
        return product -> product.getQuantity() > 0;
    }

    private static Predicate<Product> isGreaterAmountThanPrice(Purchase purchase) {
        return product -> purchase.getAmount() >= product.getPrice();
    }
    

}
