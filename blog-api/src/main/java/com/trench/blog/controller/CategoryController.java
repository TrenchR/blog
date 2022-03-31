package com.trench.blog.controller;

import com.trench.blog.service.CategoryService;
import com.trench.blog.vo.CategoryVo;
import com.trench.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Trench
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAll();
    }

    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id) {
        return categoryService.categoriesDetailById(id);
    }
}
