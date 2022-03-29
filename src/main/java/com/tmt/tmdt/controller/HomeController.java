package com.tmt.tmdt.controller;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAdvice
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;



    @GetMapping("")
    public String index(Model model) {

        List<Product> listProduct = productService.getProducts();
        model.addAttribute("products", listProduct);
        return "home";
    }



    @GetMapping("tukhoa")
    public String getProductsByName(Model model, @RequestParam(value = "q") String name) {
        if (name.equals("")) {
            return "redirect:/";
        }
        model.addAttribute("products", productService.getProductsByName(name));

        return "home";
    }

    @GetMapping("/danhmuc/{categoryId}")
    public String getProductsByCategory(Model model, @PathVariable("categoryId") Long categoryId) {
//        List<Product> products = productService.getProductsByCategory(categoryId);
//        model.addAttribute("products", products);
        return "home";
    }


}
