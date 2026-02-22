package com.binghetao.mapper;

import com.binghetao.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article(title, content, cover_img, category_id, create_user, " +
            "create_time, update_time) VALUE (#{title},#{content},#{coverImg},#{ categoryId}," +
            "#{createUser}, NOW(),NOW())")
    void add(Article article);
}
