package com.binghetao.mapper;

import com.binghetao.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article(title, content, cover_img, category_id, create_user, " +
            "create_time, update_time) VALUE (#{title},#{content},#{coverImg},#{ categoryId}," +
            "#{createUser}, NOW(),NOW())")
    void add(Article article);

    List<Article> list(Integer id, String categoryId, String state);

    @Update("UPDATE article SET title=#{title}, content=#{content}," +
            "cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=NOW() " +
            "WHERE id=#{id}")
    void update(Article article);

    @Select("SELECT * FROM article WHERE id=#{id}")
    Article getDetailById(Integer id);

    @Delete("DELETE FROM article WHERE id=#{id}")
    void deleteById(Integer id);
}
