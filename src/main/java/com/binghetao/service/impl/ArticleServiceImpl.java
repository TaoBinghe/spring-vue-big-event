package com.binghetao.service.impl;

import com.binghetao.mapper.ArticleMapper;
import com.binghetao.pojo.Article;
import com.binghetao.pojo.PageBean;
import com.binghetao.service.ArticleService;
import com.binghetao.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        PageBean<Article> pb = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Article> list=articleMapper.list(id, categoryId, state);

        Page<Article> pg= (Page<Article>) list;
        pb.setTotal(pg.getTotal());
        pb.setItems(pg.getResult());
        return pb;
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

}
