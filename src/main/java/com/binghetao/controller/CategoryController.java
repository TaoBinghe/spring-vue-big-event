package com.binghetao.controller;

import com.binghetao.pojo.Category;
import com.binghetao.pojo.Result;
import com.binghetao.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        return Result.success(categoryService.findById(id));
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }
}
