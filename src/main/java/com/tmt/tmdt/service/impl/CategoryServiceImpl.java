package com.tmt.tmdt.service.impl;

import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.repository.CategoryRepo;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo cateRepository;
    private final ProductService productService;

    //có 2 chỗ cần handle exception : getById và thêm data dùng validation
    @Override
    public Category getCategory(Integer id) {
        //nếu như findById là null thì ta không thể get()(exception)
        //nếu v thì phải return Optional, vậy nên cần phải custom cho phải return null
        //để controller còn biết mà xữ lý trả về.


        //ta cũng có thể custom để return 1 page ngay trong service

        return cateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(">>>Category with id" + id + " not found");
                    return new ResourceNotFoundException("Category with id " + id + " not found");
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
        //uncheckend exception handler
        category.setCode(TextUtil.generateCode(category.getName()));
        cateRepository.save(category);
    }

    @Override
    public Integer deleteById(Integer id) {

        // có thể try catch và return null nếu không thể xóa
//        try {
//            cateRepository.deleteById(id);
//            return id;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
        //otherwise :  Có thể bắt runtime exception và trả về 1 page lỗi ngay trong service


        cateRepository.deleteById(id);
        return id;


    }

    @Override
    public Integer[] deleteCategories(Integer[] ids) {


        for (Integer id : ids) {
            cateRepository.deleteById(id);
        }
        return ids;

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

        return cateRepository.findByName(name);

    }

    @Override
    public Page<Category> getCategoriesByNameLike(String name, Pageable pageable) {
        return cateRepository.getCategoriesByNameLike(name, pageable);
    }


    @Override
    @Transactional

    public Category addProductToCategory(Integer cateId, Long productId) {
        Category cate = getCategory(cateId);
        Product product = productService.getProduct(productId);
        cate.getProducts().add(product);
        return cate;
    }

    @Override


    public Category removeProductFromCategory(Integer cateId, Long productId) {
        Category cate = getCategory(cateId);
        Product product = productService.getProduct(productId);
        cate.getProducts().remove(product);
        return cate;
    }


}
