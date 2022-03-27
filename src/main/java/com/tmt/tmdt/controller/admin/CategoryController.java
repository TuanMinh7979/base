package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.response.ViewApi;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.service.CategoryService;
import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/category")
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("")
    public String index() {

        return "admin/category/index";
    }

    @GetMapping("viewApi")
    @ResponseBody
    public ViewApi<List<Category>> getCategories(Model model,
                                                 @RequestParam(name = "page", required = false) String pageParam,
                                                 @RequestParam(name = "limit", required = false) String limitParam,
                                                 @RequestParam(name = "sortBy", required = false) String sortBy,
                                                 @RequestParam(name = "sortDirection", required = false) String sortDirection,
                                                 @RequestParam(name = "searchNameTerm", required = false) String searchNameTerm) {


        String sortField = sortBy == null ? "id" : sortBy;
        Sort sort = (sortDirection == null || sortDirection.equals("asc")) ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam) - 1;
        int limit = limitParam == null ? 3 : Integer.parseInt(limitParam);

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page categoryPage = null;
        if (searchNameTerm != null && !searchNameTerm.isEmpty()) {

            categoryPage = categoryService.getCategoriesByNameLike(searchNameTerm, pageable);
        } else {
            categoryPage = categoryService.getCategories(pageable);
        }
        List data = categoryPage.getContent();
        int totalPage = categoryPage.getTotalPages();

        return new ViewApi<>(totalPage, data);
    }


    @PostMapping("save")
    //rest api save => add(post), edit(put)
    public String save(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (categoryService.existByName(category.getName())) {
//            result.rejectValue("name", "category.nameExist", "Loi xay ra");
            result.rejectValue("name", "nameIsExist");

        }
        if (!result.hasErrors()) {

            categoryService.save(category);
            return "redirect:/admin/category";
        }

        return "admin/category/add";


    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable Long id) {

        if (categoryService.deleteById(id) != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


    }

    @PostMapping("delete")
    public ResponseEntity<Long[]> deleteCategories(@RequestBody Long[] ids) {
        System.out.println(ids.length);
        if (categoryService.deleteCategories(ids) != null) {
            return new ResponseEntity<>(ids, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


    }


    @GetMapping("edit/{idx}")
    //rest api : showUpdateForm , showAddForm => getCategory(get)(just for update)
    public String showUpdateForm(Model model, @PathVariable("idx") String idx) {

        Category category = null;
        try {
            Long id = Long.parseLong(idx);
            category = categoryService.getCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
            category = categoryService.getCategoryByName(idx);
        }
        if (category == null) {
            model.addAttribute("message", "Category not found");
            return "admin/category/error";
        }
        model.addAttribute("category", category);
        return "admin/category/edit";

    }

    @GetMapping("add")
    public String showAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/add";

    }


}
