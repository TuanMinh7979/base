package com.tmt.tmdt.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ProductRepo;
import com.tmt.tmdt.service.ImageDetailService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ImageDetailService imageDetailService;
    private final Cloudinary cloudinary;
    private final ProductRepo productRepo;

    @Override
    public Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + id + " not found")
                );
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
    @Transactional
    public Product save(Product product, MultipartFile[] files) throws IOException {
        product.setCode(TextUtil.generateCode(product.getName()));

        if (!product.getFile().isEmpty()) {
            Map rs = cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            product.setImage((String) rs.get("url"));
        }
        Product productSaved = productRepo.save(product);


        for (MultipartFile filei : files) {

            if (!filei.isEmpty()) {
                Map rsi = cloudinary.uploader().upload(filei.getBytes(), ObjectUtils.asMap("resource_type", "auto"));


                ImageDetail imageDetail = new ImageDetail();
                imageDetail.setPublicId((String) rsi.get("public_id"));
                imageDetail.setLink((String) rsi.get("url"));
                imageDetail.setProduct(productSaved);
                imageDetailService.save(imageDetail);
            }
        }


        return productSaved;
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
    public void deleteById(Long id) {
        //sql error
        //return null to sign that error happened
        productRepo.deleteById(id);

    }

    @Override
    public void deleteProducts(Long[] ids) {
        for (Long id : ids) {
            productRepo.deleteById(id);
        }
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

    @Override
    public Product addImageDetailToProduct(Long productId, Long imageDetailId) {
        Product product = getProduct(productId);
        ImageDetail imageDetail = imageDetailService.getImageDetail(imageDetailId);
        product.getImages().add(imageDetail);
        return product;
    }

    @Override
    public Product removeImageDetailFromProduct(Long productId, Long imageDetailId) throws IOException {
        imageDetailService.deleteFromCloud(imageDetailId);
        Product product = getProduct(productId);
        ImageDetail imageDetail = imageDetailService.getImageDetail(imageDetailId);
        product.getImages().remove(imageDetail);
        return product;
    }


}
