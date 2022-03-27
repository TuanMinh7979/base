package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();

    //for auto complete search
    List<String> getNamesByKw(String kw);

    //for input search by keyword
    List<Product> getProductsByName(String name);

    Product getProduct(Long id);

    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProducts();

    boolean existByName(String name);

    Product save(Product product);


}
