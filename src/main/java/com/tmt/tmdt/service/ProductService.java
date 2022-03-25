package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();

    List<String> getNamesByKw(String kw);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProducts();
}
