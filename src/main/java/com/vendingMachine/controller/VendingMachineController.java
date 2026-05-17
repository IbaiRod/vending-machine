package com.vendingMachine.controller;

import com.vendingMachine.model.dto.PurchaseRequest;
import com.vendingMachine.model.dto.PurchaseResponse;
import com.vendingMachine.model.entity.Product;
import com.vendingMachine.model.entity.Purchase;
import com.vendingMachine.service.VendingMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vendingMachine")
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/showProducts")
    public List<Product> showProducts() {

        return vendingMachineService.getProductList();

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value="/insertMoney")
    public Purchase saveCoins(@RequestBody PurchaseRequest purchaseRequest) {
        
        return vendingMachineService.sumCoins(purchaseRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/buy")
    public PurchaseResponse buyProduct(@RequestParam Integer idProducto, @RequestParam Integer idPurchase) {

        return vendingMachineService.buyProcess(idProducto, idPurchase);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/purchase/{purchaseId}")
    public Purchase getPurchaseById(@PathVariable Integer purchaseId) {
        return vendingMachineService.getPurchaseById(purchaseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value="/purchase/{purchaseId}")
    public Double getPurchaseRefund(@PathVariable Integer purchaseId) {
        return vendingMachineService.getPurchaseRefund(purchaseId);
    }

}
