package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.response.ViewApi;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;


    @GetMapping("")
    public String index(Model model) {

//        model.addAttribute("categories", categoryService.getCategories());
        return "admin/product/index";
    }

    @GetMapping("add")
    public String showAddForm(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/product/add";

    }

    @GetMapping("api/viewApi")
    @ResponseBody
    public ViewApi<List<Product>> getCategories(Model model,
                                                @RequestParam(name = "page", required = false) String pageParam,
                                                @RequestParam(name = "limit", required = false) String limitParam,
                                                @RequestParam(name = "sortBy", required = false) String sortBy,
                                                @RequestParam(name = "sortDirection", required = false) String sortDirection,
                                                @RequestParam(name = "searchNameTerm", required = false) String searchNameTerm,
                                                @RequestParam(name = "category", required = false) String categoryIdParam) {


        String sortField = sortBy == null ? "id" : sortBy;
        Sort sort = (sortDirection == null || sortDirection.equals("asc")) ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);
        int page = pageParam == null ? 0 : Integer.parseInt(pageParam) - 1;
        int limit = limitParam == null ? 5 : Integer.parseInt(limitParam);

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page productPage = null;

        if (categoryIdParam != null && !categoryIdParam.isEmpty() && Long.parseLong(categoryIdParam) != 0) {
            //get product by category
            Long categoryId = Long.parseLong(categoryIdParam);
            if (searchNameTerm != null && !searchNameTerm.isEmpty()) {
                productPage = productService.getProductsByCategoryAndNameLike(categoryId, searchNameTerm, pageable);
            } else {
                productPage = productService.getProductsByCategory(categoryId, pageable);
            }


        } else if (searchNameTerm != null && !searchNameTerm.isEmpty()) {

            productPage = productService.getProductsByName(searchNameTerm, pageable);
        } else {
            productPage = productService.getProducts(pageable);
        }
        List data = productPage.getContent();
        int totalPage = productPage.getTotalPages();


        return new ViewApi<>(totalPage, data);
    }


    @PostMapping("save")
    public String save(Model model, @RequestParam("imageFiles") MultipartFile[] files,
                       @Valid @ModelAttribute("product") Product product, BindingResult result) {
        //Phai dat block nay o tren vi blog se phat sinh loi xuat hien o block duoi


        if (productService.existByName(product.getName())) {
            result.rejectValue("name", "nameIsExist");

        }

        if (!result.hasErrors()) {
            try {
                productService.save(product, files);
                return "redirect:/admin/product";
            } catch (IOException e) {
                model.addAttribute("message", "Can not read your file, try again please!");
                //ioe
                return "admin/product/add";
            }
            //success

        }

        //handle loi bindding
        return "admin/product/add";

    }

    @PostMapping("update")
    public String update(Model model,@RequestParam("imageFiles") MultipartFile[] files, @Valid @ModelAttribute("product") Product product, BindingResult result) {

        if (!result.hasErrors()) {
            try {
                productService.save(product, files);
            } catch (IOException e) {
                model.addAttribute("message", "Can not read your file, try again please!");

                return "admin/product/edit";
            }
            //success
            return "redirect:/admin/product";
        }

        System.out.println("---------------------------------------------");
        result.getAllErrors().forEach(System.out::println);
        return "admin/product/edit";

    }


    @GetMapping("edit/{idx}")
    //rest api : showUpdateForm , showAddForm => getCategory(get)(just for update)
    public String showUpdateForm(Model model, @PathVariable("idx") String idx) {

        Product product = null;
        try {
            //Catch casting exception
            Long id = Long.parseLong(idx);
            product = productService.getProduct(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            product = productService.getProductByName(idx);
        }
        //other exception will be handled in service
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "admin/product/edit";

    }

    @PostMapping("api/delete/{id}")
    //call with ajax
    public ResponseEntity<Long> deleteCategory(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("api/delete")
    public ResponseEntity<Long[]> deleteProduct(@RequestBody Long[] ids) {

        productService.deleteProducts(ids);
        return new ResponseEntity<>(ids, HttpStatus.OK);

    }


}




