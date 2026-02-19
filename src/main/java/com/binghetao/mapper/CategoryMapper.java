package com.binghetao.mapper;

import com.binghetao.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("INSERT INTO category(category_name, category_alias, create_user, create_time, update_time)" +
            " VALUE(#{categoryName},#{categoryAlias}, #{createUser}, NOW(), NOW()) ")
    void add(Category category);

    @Select("SELECT * FROM category WHERE create_user=#{id}")
    List<Category> list(Integer id);

    @Select("SELECT * FROM category WHERE id=#{id}")
    Category findById(Integer id);

    @Update("UPDATE category SET category_name=#{categoryName}, category_alias=#{categoryAlias}," +
            "update_time=NOW() WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    void deleteById(Integer id);
}
