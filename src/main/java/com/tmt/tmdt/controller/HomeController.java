package com.tmt.tmdt.controller;

import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        return "home/home";
    }

    @GetMapping("tukhoa")
    public String getProductsByName(Model model, @RequestParam(value = "q") String name) {
        if (name.equals("")) {
            return "redirect:/";
        }
        model.addAttribute("products", productService.getProductsByName(name));

        return "home/home";
    }

    @GetMapping("/danhmuc/{categoryId}")
    public String getProductsByCategory(Model model, @PathVariable("categoryId") Long categoryId) {
//        List<Product> products = productService.getProductsByCategory(categoryId);
//        model.addAttribute("products", products);
        return "home/home";
    }


}
