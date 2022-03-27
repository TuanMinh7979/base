package com.tmt.tmdt.controller.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.entities.ImageDetail;
import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.service.ImageDetailService;
import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller

@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductController {
    private final Cloudinary cloudinary;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ImageDetailService imageDetailService;

    @GetMapping("")
    public String index() {
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

    @PostMapping("save")

    public String save(Model model, @Valid @ModelAttribute("product") Product product, BindingResult result) {
        //Phai dat block nay o tren vi blog se phat sinh loi xuat hien o block duoi
        if (productService.existByName(product.getName())) {
            result.rejectValue("name", "nameIsExist");

        }

        if (!result.hasErrors()) {

            try {
                Map rs = cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                product.setImage((String) rs.get("url"));
                Product productSaved = productService.save(product);
                for (MultipartFile filei : product.getFiles()) {
                    Map rsi = cloudinary.uploader().upload(filei.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    imageDetailService.save(new ImageDetail((String) rsi.get("url"), productSaved));
                }
                return "redirect:/admin/product";

            } catch (IOException e) {
                System.err.println("Add product" + e.getMessage());
                //loi them anh
                //neu co sai thi chuong trinh van chay ve trang add o phia duoi
            }
        }

        return "admin/product/add";

    }
}




