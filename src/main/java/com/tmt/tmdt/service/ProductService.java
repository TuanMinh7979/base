package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    //for auto complete search
    List<String> getNamesByKw(String kw);

    //for input search by keyword
    Page<Product> getProductsByName(String name, Pageable pageable);

    List<Product> getProductsByName(String name);

    Product getProductByName(String name);

    Product getProduct(Long id);

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    List<Product> getProducts();

    boolean existByName(String name);

    Product save(Product product, MultipartFile[] files) throws IOException;

    Page<Product> getProductsByCategoryAndNameLike(Long categoryId, String name, Pageable pageable);

    Page getProducts(Pageable pageable);

    void deleteById(Long id);

    void deleteProducts(Long[] ids);

    Product addImageDetailToProduct(Long productId, Long imageDetailId );
    Product removeImageDetailFromProduct(Long productId, Long imageDetailId ) throws IOException;


}
