package com.binghetao.service;

import com.binghetao.pojo.Article;
import com.binghetao.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    void update(Article article);

    Article getDetailById(Integer id);

    void deleteById(Integer id);
}
