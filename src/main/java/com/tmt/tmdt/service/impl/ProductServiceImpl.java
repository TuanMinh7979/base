package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ProductRepository;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    public Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn(">>>Product with id" + id + " not found");
                    return new ResourceNotFoundException("Product with id " + id + " not found");
                });
    }

    @Override
    public List<Product> getProducts() {
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
    public boolean existByName(String name) {
        return productRepo.existsByName(name);
    }


    @Override
    public Product save(Product product) {
        product.setCode(TextUtil.generateCode(product.getName()));
        return productRepo.save(product);
    }

    @Override
    public Page<Product> getProductsByCategoryAndNameLike(Long categoryId, String name, Pageable pageable) {
        return productRepo.getProductsByCategoryAndNameLike(categoryId, name, pageable);
    }

    @Override
    public Page getProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }


    @Override
    public Long deleteById(Long id) {
        //sql error
        //return null to sign that error happened
        productRepo.deleteById(id);
        return id;
    }

    @Override
    public Page<Product> getProductsByName(String name, Pageable pageable) {
        return productRepo.getProductsByName(name, pageable);
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepo.getProductsByCategory(categoryId, pageable);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepo.getProductByName(name);
    }
}
