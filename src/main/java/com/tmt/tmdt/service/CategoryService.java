package com.tmt.tmdt.service;

import com.tmt.tmdt.dto.response.CategoryResponse;
import com.tmt.tmdt.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CategoryService {


    Category getCategory(Integer id);

    List<Category> getCategories();

    Page getCategories(Pageable p);




    void deleteById(Integer id);

    void deleteCategories(Integer[] ids);

    boolean existByName(String name);

    List<String> getCategoryNamesByKw(String kw);

    Category getCategoryByName(String name);


    Page<Category> getCategoriesByNameLike(String name, Pageable pageable);

    Category addProductToCategory(Integer cateId, Long productId);

    Category removeProductFromCategory(Integer cateId, Long productId);

    List<Category> getCategoriesInHierarchical();








<<<<<<< HEAD
    List<Category> getCategoriesInHierarchicalFromRootWithOut(int i);

    Category add(Category category);

    Category save(Category category);


=======
>>>>>>> 5a3620c836045d0dd27a1b5f5e10b804be317485
}
