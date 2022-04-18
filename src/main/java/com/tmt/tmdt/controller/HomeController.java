package com.tmt.tmdt.controller;

import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    //    @ModelAttribute
//    public void commonAtr(Model model) {
//        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());
//
//    }
    @GetMapping("")
    public String index(Model model) {

//        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());
        return "home/home";
    }

    @GetMapping("api/category/{categoryId}")
    @ResponseBody
    public List<Product> getProducts(@PathVariable("categoryId") Integer categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return products;

    }


}





