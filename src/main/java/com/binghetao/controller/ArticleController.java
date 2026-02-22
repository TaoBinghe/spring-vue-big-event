package com.binghetao.controller;

import com.binghetao.pojo.Article;
import com.binghetao.pojo.PageBean;
import com.binghetao.pojo.Result;
import com.binghetao.service.ArticleService;
import com.binghetao.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) String categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }
}
