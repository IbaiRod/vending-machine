package com.vendingMachine.infrastructure.web;

import com.vendingMachine.application.dto.PurchaseRequest;
import com.vendingMachine.application.dto.PurchaseResponse;
import com.vendingMachine.application.port.input.VendingMachineUseCase;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.domain.model.Purchase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/vendingMachine")
public class VendingMachineController {

    private final VendingMachineUseCase vendingMachineUseCase;

    public VendingMachineController(VendingMachineUseCase vendingMachineUseCase) {
        this.vendingMachineUseCase = vendingMachineUseCase;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/showProducts")
    public List<Product> showProducts() {
        return vendingMachineUseCase.showProducts();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/insertMoney")
    public Purchase insertMoney(@RequestBody PurchaseRequest request) {
        return vendingMachineUseCase.insertMoney(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buy")
    public PurchaseResponse buyProduct(@RequestParam Integer idProducto, @RequestParam Integer idPurchase) {
        return vendingMachineUseCase.buyProduct(idProducto, idPurchase);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/purchase/{purchaseId}")
    public Purchase getPurchaseById(@PathVariable Integer purchaseId) {
        return vendingMachineUseCase.findPurchaseById(purchaseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/purchase/{purchaseId}")
    public BigDecimal refundPurchase(@PathVariable Integer purchaseId) {
        return vendingMachineUseCase.refundPurchase(purchaseId);
    }
}
