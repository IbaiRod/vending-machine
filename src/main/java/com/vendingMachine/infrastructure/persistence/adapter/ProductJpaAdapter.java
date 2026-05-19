package com.vendingMachine.infrastructure.persistence.adapter;

import com.vendingMachine.application.port.output.ProductRepositoryPort;
import com.vendingMachine.domain.model.Product;
import com.vendingMachine.infrastructure.persistence.mapper.ProductMapper;
import com.vendingMachine.infrastructure.persistence.repository.SpringProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductJpaAdapter implements ProductRepositoryPort {

    private final SpringProductRepository springRepository;
    private final ProductMapper mapper;

    public ProductJpaAdapter(SpringProductRepository springRepository, ProductMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> findAll() {

        return springRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {

        return springRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Product save(Product product) {

        var entity = mapper.toEntity(product);

        var saved = springRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public long count() {

        return springRepository.count();
    }
}
