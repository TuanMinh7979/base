package com.tmt.tmdt.controller.admin;

import com.tmt.tmdt.dto.ViewResponseApi;
import com.tmt.tmdt.entities.Attribute;
import com.tmt.tmdt.entities.Category;
import com.tmt.tmdt.service.CategoryService;
import com.tmt.tmdt.util.GeneratedId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/category")
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("")
    public String index() {
        return "admin/category/index";
    }

    @GetMapping("api/viewApi")
    @ResponseBody
    public ViewResponseApi<List<Category>> getCategories(Model model,
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

        return new ViewResponseApi<>(totalPage, data);
    }

    //ADD
    @GetMapping("add")
    public String showAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);

        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());
        return "admin/category/add";
    }


    @Transactional
    @PostMapping("add")
    //rest api save => add(post), edit(put)
    public String add(Model model, @Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (categoryService.existByName(category.getName())) {
            result.rejectValue("name", "nameIsExist");
        }

        if (!result.hasErrors()) {
            //when create new generate dynamicalyy a attribute example

            Category categorySaved = categoryService.save(category);
            categorySaved.getAttributes().add(new Attribute("exam" + categorySaved.getId(), "examName", Arrays.asList("examValue"), 0));
            return "redirect:/admin/category";
        }
        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());

        return "admin/category/add";
    }
    //-ADD

    //UPDATE
    @GetMapping("update/{idx}")
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

    @PostMapping("update")
    //rest api save => add(post), update(put)
    //FOR UPDATE: update in rest api must a id in path. but in mvc dont need it
    public String update(Model model, @Valid @ModelAttribute("category") Category category, BindingResult result) {

        if (!result.hasErrors()) {
            categoryService.save(category);
            return "redirect:/admin/category";
        }
        model.addAttribute("categoriesForForm", categoryService.getCategoriesInHierarchical());

        return "admin/category/edit";
    }
    //-UPDATE


    //DELETE
    @PostMapping("api/delete/{id}")
    @ResponseBody
    //call by ajax
    public ResponseEntity<Integer> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("api/delete")
    public ResponseEntity<Integer[]> deleteCategories(@RequestBody Integer[] ids) {
        categoryService.deleteCategories(ids);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
    //-DELETE


    //FOR ATTRIBUTE

    @GetMapping("api/{id}/attributes")
    @ResponseBody
    public Set<Attribute> getAttributesByCategoryId(@PathVariable("id") Integer id) {
        return categoryService.getCategory(id).getAttributes();
    }

    @PostMapping("api/{categoryId}/attributes/value")
    @ResponseBody
    public List<Object> getAttributeValueByAttributeId(@PathVariable("categoryId") Integer categoryId,
                                                       @RequestBody Map<String, String> data) {

        Set<Attribute> ats = categoryService.getCategory(categoryId).getAttributes();
        for (Attribute ati : ats) {
            if (ati.getId().equals(data.get("id"))) {
                return ati.getValue();
            }
        }
        return null;
    }

    @PostMapping("api/{id}/attributes/add")
    @ResponseBody
    public ResponseEntity<Attribute> addAttribute(@PathVariable(value = "id") Integer id,
                                                  @RequestBody Attribute attribute) {

        Category category = categoryService.getCategory(id);
        attribute.setId(GeneratedId.generateRandomPassword(3));

        category.getAttributes().add(attribute);
        categoryService.save(category);

        return new ResponseEntity<>(attribute, HttpStatus.OK);

    }

    @PostMapping("api/{categoryId}/attributes/update")
    @ResponseBody
    public ResponseEntity<Attribute> updateAttribute(@PathVariable(value = "categoryId") Integer id,
                                                     @RequestBody Attribute newAttribute) {

        Category category = categoryService.getCategory(id);


        Attribute oldAttribute = category.getAttributes()
                .stream()
                .filter(a -> a.getId().equals(newAttribute.getId()))
                .collect(Collectors.toList())
                .get(0);

        Set<Attribute> attributes = category.getAttributes();
        attributes.remove(oldAttribute);
        attributes.add(newAttribute);
        category.setAttributes(attributes);
        categoryService.save(category);

        return new ResponseEntity<>(newAttribute, HttpStatus.OK);

    }

    @PostMapping("api/{categoryId}/attributes/delete")
    @ResponseBody
    public ResponseEntity<Attribute> deteteAttribute(@PathVariable(value = "categoryId") Integer id,
//                                                     @RequestBody Map<String, String> data) {
                                                     @RequestBody String data) {
        String idToDel = data.substring(1, data.length() - 1);

        Category category = categoryService.getCategory(id);


        Attribute oldAttribute = category.getAttributes()
                .stream()
                .filter(a -> a.getId().equals(idToDel))
                .collect(Collectors.toList())
                .get(0);


        Set<Attribute> attributes = category.getAttributes();
        attributes.remove(oldAttribute);
        category.setAttributes(attributes);
        categoryService.save(category);

        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @PostMapping("api/{categoryId}/attributes/deletes")
    @ResponseBody
    public ResponseEntity<List<String>> deteteAttributes(@PathVariable(value = "categoryId") Integer id,
                                                         @RequestBody List<String> data) {
        Category category = categoryService.getCategory(id);
        Set<Attribute> attributes = category.getAttributes();
        Set<Attribute> attributeToDels = new HashSet<>();
        for (String idi : data) {
            for (Attribute attribute : attributes) {
                if (attribute.getId().equals(idi)) {
                    attributeToDels.add(attribute);
                    break;
                }
            }

        }
        for (Attribute attributeToDel : attributeToDels) {
            attributes.remove(attributeToDel);
        }
        category.setAttributes(attributes);
        categoryService.save(category);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    //-FOR ATTRIBUTE


}
