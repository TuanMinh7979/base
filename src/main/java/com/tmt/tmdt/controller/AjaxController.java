package com.tmt.tmdt.controller;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.repository.ProductRepo;
import com.tmt.tmdt.repository.TestRepo;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ImageService;
import com.tmt.tmdt.service.ProductService;
import com.tmt.tmdt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ajax")
public class AjaxController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final ImageService imageService;

    private final ProductRepo productRepo;
    private final TestRepo testRepo;


    @GetMapping("autocomplete-search/product")
    public List<String> getProductNamesByKw(@RequestParam("term") String kw) {
        return productService.getNamesByKw(kw);
    }

    @GetMapping("autocomplete-search/category")
    public List<String> getCategoryNamesByKw(@RequestParam("term") String kw) {
        return categoryService.getCategoryNamesByKw(kw);
    }

    @GetMapping("autocomplete-search/role")
    public List<String> getRoleNamesByKw(@RequestParam("term") String kw) {
        return roleService.getRoleNamesByKw(kw);
    }

    @GetMapping("test/deleteImg/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            imageService.deleteById(id);
            return "delete succeess";
        } catch (IOException e) {
            e.printStackTrace();
            return "can not delete";
        }


    }

    @GetMapping("test/deleteImgfromProduct/{pid}/{iid}")
    public String delete(@PathVariable("pid") Long pid, @PathVariable("iid") Long iid) {
        try {
            productService.removeImageDetailFromProduct(pid, iid);
            return "delete succeess";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed ";
        }


    }

    @GetMapping("abc/{pid}/{cid}")
    public String deltest(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Product product = productService.getProduct(pid);
        product.removeImage(imageService.getImageDetail(cid));
        productRepo.save(product);
        return "sucess";

    }

    //
    @GetMapping("abc1/{pid}/{cid}")
    public String deltest1(@PathVariable("pid") Long pid, @PathVariable("cid") Long cid) {
        Product product = productService.getProduct(pid);
        product.removeTest(testRepo.findById(cid).get());
        productRepo.save(product);
        return "sucess";

    }


}
