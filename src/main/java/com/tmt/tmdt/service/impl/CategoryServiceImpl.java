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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository cateRepository;

    @Override
    public Category getCategory(Long id) {
        Category category = cateRepository.findById(id).get();
        if (category == null) {
            log.warn("category with id: " + id + " not found");
        }
        return category;
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


}
