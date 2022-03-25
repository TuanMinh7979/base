package com.tmt.tmdt.controller;

import com.tmt.tmdt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ajax")
public class AjaxController {
    private final ProductService productService;

    @ResponseBody
    @GetMapping("autocomplete-search")
    public List<String> getNamesByKw(@RequestParam("term") String kw) {
        return productService.getNamesByKw(kw);
    }


}
