package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.CategoryRepository;
import com.tmt.tmdt.repository.ProductRepository;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository cateRepository;
    private final ProductService productService;

    //có 2 chỗ cần handle exception : getById và thêm data dùng validation
    @Override
    public Category getCategory(Long id) {
        //nếu như findById là null thì ta không thể get()(exception)
        //nếu v thì phải return Optional, vậy nên cần phải custom cho phải return null
        //để controller còn biết mà xữ lý trả về.
        return cateRepository.findById(id)
                .orElseGet(() -> {
                    log.warn(">>>Not found category with id: " + id);
                    return null;

                });

    }

    @Override
    public List<Category> getCategories() {
        return cateRepository.findAll();
    }

    @Override
    public Page getCategories(Pageable p) {
        return cateRepository.findAll(p);
    }

    @Override
    public void save(Category category) {
        category.setCode(TextUtil.generateCode(category.getName()));
        cateRepository.save(category);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            cateRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public Long[] deleteCategories(Long[] ids) {
        try {

            for (Long id : ids) {
                cateRepository.deleteById(id);
            }
            return ids;

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean existByName(String name) {
        return cateRepository.existsByName(name);
    }

    @Override
    public List<String> getCategoryNamesByKw(String kw) {
        return cateRepository.getCategoryNamesByKw(kw);
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = cateRepository.findByName(name);
        if (category == null) {
            log.warn(">>> Not found category with name " + name);
        }
        return category;
    }

    @Override
    public Page<Category> getCategoriesByNameLike(String name, Pageable pageable) {
        return cateRepository.getCategoriesByNameLike(name, pageable);
    }

    @Transactional
    public Category addProductToCategory(Long cateId, Long productId) {
        Category cate = getCategory(cateId);
        Product product = productService.getProduct(productId);
        cate.getProducts().add(product);
        return cate;
    }

    @Transactional
    public Category removeProductFromCategory(Long cateId, Long productId) {
        Category cate = getCategory(cateId);
        Product product = productService.getProduct(productId);
        cate.getProducts().remove(product);
        return cate;
    }


}
