package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ProductRepository;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<String> getNamesByKw(String kw) {

        List<String> productNames =
                productRepo.getNamesByKw(kw);

        return productNames;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.getProductsByName(name);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.getProductsByCategory(categoryId);
    }

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }




}
