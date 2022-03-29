package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice

@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;

    //
    @ModelAttribute
    public void commonAtr(Model model) {
        model.addAttribute("categories", this.categoryService.getCategories());
    }

    @GetMapping("")
    public String index() {
        return "admin/admin";
    }

}
