package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("multiplefile")
    public String test2(@RequestParam("image") MultipartFile[] file) {
        System.out.println(file.length);
        return "redirect:/";
    }

}
