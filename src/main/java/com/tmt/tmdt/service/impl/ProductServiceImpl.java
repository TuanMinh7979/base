package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ProductRepository;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
//    private final CategoryService categoryService;

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
    public Product getProduct(Long id) {

        return productRepo.findById(id)
                .orElseGet(() -> {
                    log.warn(">>>Not found product with id: " + id);
                    return null;

                });
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.getProductsByCategory(categoryId);
    }

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }


    @Override
    public boolean existByName(String name) {
        return productRepo.existsByName(name);
    }


    @Override
    public Product save(Product product) {
        product.setCode(TextUtil.generateCode(product.getName()));
        return productRepo.save(product);
    }


}
