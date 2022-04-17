package com.tmt.tmdt.controller;

import com.tmt.tmdt.entities.Product;
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





}
