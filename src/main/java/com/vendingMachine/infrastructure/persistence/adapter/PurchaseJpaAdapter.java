package com.vendingMachine.infrastructure.persistence.adapter;

import com.vendingMachine.application.port.output.PurchaseRepositoryPort;
import com.vendingMachine.domain.model.Purchase;
import com.vendingMachine.infrastructure.persistence.mapper.PurchaseMapper;
import com.vendingMachine.infrastructure.persistence.repository.SpringPurchaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PurchaseJpaAdapter implements PurchaseRepositoryPort {

    private final SpringPurchaseRepository springRepository;
    private final PurchaseMapper mapper;

    public PurchaseJpaAdapter(SpringPurchaseRepository springRepository, PurchaseMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Purchase save(Purchase purchase) {
        var entity = mapper.toEntity(purchase);
        var saved = springRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        return springRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springRepository.deleteById(id);
    }
}
