package com.tmt.tmdt.service;

import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {


    Category getCategory(Long id);

    List<Category> getCategories();

    Page getCategories(Pageable p);


    void save(Category category);

    Long deleteById(Long id);

    Long[] deleteCategories(Long[] ids);

    boolean existByName(String name);

    List<String> getCategoryNamesByKw(String kw);
    Category getCategoryByName(String name);


    Page<Category> getCategoriesByNameLike(String name, Pageable pageable);

    Category addProductToCategory(Long cateId, Long productId);
    Category removeProductFromCategory(Long cateId, Long productId);
}
