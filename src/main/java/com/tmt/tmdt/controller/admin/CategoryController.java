package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.response.ViewApi;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.exception.ResourceNotFoundException;
import com.tmt.tmdt.service.CategoryService;
import lombok.RequiredArgsConstructor;
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

//    @GetMapping("/api/all")
//    @ResponseBody
//    public List<Category> testapi() {
//        System.out.println("==========================");
//        categoryService.getCategories().get(0).getProducts().forEach((p) -> {
//            System.out.println("*************************"+p.getName());
//            //with EAGER can get with get method but can not render json because open-session-in-view-layer= false
//            //bug:,"products":[{"createAt":"2022-03-29T15:26:12.056281","updateAt":"2022-03-29T15:26:12.057278","id":8,"name":"ip13","price":30.00,"image":null
//            // ,"description":null,"images"}]}]{"meta":{"message":"Could not write JSON: failed to lazily initialize a collection of role:
//            // com.tmt.tmdt.entities.Product.images, could not initialize proxy - no Session; nested
//            // exception is com.fasterxml.jackson.databind.JsonMappingException: failed to lazily initialize a collection of role:
//            // com.tmt.tmdt.entities.Product.images, could not initialize proxy -
//            // no Session (through reference chain: java.util.ArrayList[0]->com.tmt.tmdt.entities.Category[\"products\"]->
//            // org.hibernate.collection.internal.PersistentSet[0]->com.tmt.tmdt.entities.Product[\"images\"])"}}
//
//            //LAZY CANNOT call getMethod even in java code
//            //bug {"meta":{"message":"failed to lazily initialize a collection of role could not initialize proxy - no Session"}}
//        });
//        System.out.println("************************* " + categoryService.getCategory(86).getProducts().size());
//
//        return categoryService.getCategories();
//    }

    @GetMapping("")
    public String index() {


        return "admin/category/index";
    }

    @GetMapping("api/viewApi")
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
        int limit = limitParam == null ? 5 : Integer.parseInt(limitParam);

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
            result.rejectValue("name", "nameIsExist");
        }

        if (!result.hasErrors()) {
            categoryService.save(category);
            return "redirect:/admin/category";
        }
        return "admin/category/add";
    }

    @PostMapping("update")
    //rest api save => add(post), edit(put)
    public String update(@Valid @ModelAttribute("category") Category category, BindingResult result) {

        if (!result.hasErrors()) {
            categoryService.save(category);
            return "redirect:/admin/category";
        }
        return "admin/category/edit";
    }


    @PostMapping("api/delete/{id}")
    @ResponseBody
    //call with ajax
    public ResponseEntity<Integer> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        //nếu xảy ra lỗi client sẽ nhận lõi 500
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("api/delete")
    public ResponseEntity<Integer[]> deleteCategories(@RequestBody Integer[] ids) {
        categoryService.deleteCategories(ids);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }


    @GetMapping("edit/{idx}")
    //rest api : showUpdateForm , showAddForm => getCategory(get)(just for update)
    public String showUpdateForm(Model model, @PathVariable("idx") String idx) {
        Category category = null;
        try {
            //Catch casting exception
            Integer id = Integer.parseInt(idx);
            category = categoryService.getCategory(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            category = categoryService.getCategoryByName(idx);
        }
        model.addAttribute("category", category);
        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());
        return "admin/category/edit";

    }

    @GetMapping("add")
    public String showAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);

        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());
        return "admin/category/add";

    }


}
