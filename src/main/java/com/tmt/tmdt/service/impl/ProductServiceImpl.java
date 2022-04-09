package com.tmt.tmdt.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tmt.tmdt.entities.Image;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.ProductRepo;
import com.tmt.tmdt.service.ImageService;
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

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableTransactionManagement

public class ProductServiceImpl implements ProductService {
    private final ImageService imageService;
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
    public Product save(Product product, MultipartFile file, MultipartFile[] files) throws IOException {
        product.setCode(TextUtil.generateCode(product.getName()));
        Product productSaved = productRepo.save(product);


        if (file != null && !file.isEmpty()) {
            Map rs = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            productSaved.setMainImageLink((String) rs.get("url"));

            Image mainImage = new Image();
            mainImage.setPublicId((String) rs.get("public_id"));
            mainImage.setLink((String) rs.get("url"));
            mainImage.setProduct(productSaved);
            mainImage.setMain(true);
            imageService.save(mainImage);
        }

        if (files == null) return productSaved;
        for (MultipartFile filei : files) {

            if (!filei.isEmpty()) {
                Map rsi = cloudinary.uploader().upload(filei.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                Image image = new Image();
                image.setPublicId((String) rsi.get("public_id"));
                image.setLink((String) rsi.get("url"));
                image.setProduct(productSaved);
                imageService.save(image);
            }
        }
        return productSaved;
    }

    @Override
    @Transactional
    public Product update(Product product, MultipartFile file, MultipartFile[] files, String delImageIds) throws IOException {
        product.setCode(TextUtil.generateCode(product.getName()));
        if (delImageIds != null && !delImageIds.isEmpty()) {
            delImageIds = delImageIds.trim();
            List<String> strIds = Arrays.asList(delImageIds.split(" "));
            List<Long> ids = strIds.stream().map(Long::parseLong).collect(Collectors.toList());
            //remove image from database (orphan removeal and deleit in cloud)
            for (Long idToDel : ids) {
                imageService.deleteById(idToDel);
            }

        }
        Product productSaved = productRepo.save(product);
        if (file != null && !file.isEmpty()) {
            Map rs = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            productSaved.setMainImageLink((String) rs.get("url"));

            Image mainImage = new Image();
            mainImage.setPublicId((String) rs.get("public_id"));
            mainImage.setLink((String) rs.get("url"));
            mainImage.setProduct(productSaved);
            mainImage.setMain(true);
            imageService.save(mainImage);
        }


        //set and save product extra image
        if (files == null) return productSaved;
        for (MultipartFile filei : files) {
            if (!filei.isEmpty()) {
                Map rsi = cloudinary.uploader().upload(filei.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                Image image = new Image();
                image.setPublicId((String) rsi.get("public_id"));
                image.setLink((String) rsi.get("url"));
                image.setProduct(productSaved);
                imageService.save(image);
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
    public Product getProductWithImages(Long id) {
//        Product product = getProduct(id);
        Product productWithImages = productRepo.getProductWithImages(id);
        return productWithImages;
    }


}
