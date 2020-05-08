package com.hcm.tms.controller;

import com.hcm.tms.entity.Category;
import com.hcm.tms.entity.User;
import com.hcm.tms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/getAllCategory")
    public String getAllCategory(Model model){
        List<Category> categories = categoryService.getAll();
        model.addAttribute("category",new Category());
        model.addAttribute("categories",categories);
        return "listCategory";
    }

    @PostMapping(value = "/createCategory")
    public String createCategory(@RequestBody Category category, Model model){

        categoryService.addCategory(category);

        return "redirect:/admin/category/getAllCategory";
    }

//    @GetMapping("/editCategory/{id}")
//    public String GetCategoryToEdit(Model model, @PathVariable("id") Long id) {
//        Optional<Category> category = categoryService.getCategoryById(id);
//
//            model.addAttribute("category", category.get());
//            return "listCategory";
//
//    }
    @PostMapping(value = "/editCategory")
    public String EditCategory(@RequestBody Category category, Model model){
        categoryService.addCategory(category);
        return "redirect:/admin/category/getAllCategory";
    }

    @GetMapping(value = "/checkExist/{name}")
    public ResponseEntity checkIfExist(@PathVariable String name, @RequestParam(required = false) Long id) {
        Optional<Category> category = categoryService.getCategoryByName(name);
        if(category.isPresent() && !category.get().getCategoryId().equals(id))
            return ResponseEntity.ok("true");
        else
            return ResponseEntity.ok("false");
    }
}


