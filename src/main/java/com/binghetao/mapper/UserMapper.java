package com.binghetao.mapper;

import com.binghetao.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username=#{username}")
    User findByUserName(String username);

    @Insert("INSERT INTO user(username, password, create_time, update_time)" +
            " values(#{username}, #{password }, now(), now())")
    void add(String username, String password);
}
