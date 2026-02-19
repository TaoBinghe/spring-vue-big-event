package com.binghetao.service;

import com.binghetao.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
