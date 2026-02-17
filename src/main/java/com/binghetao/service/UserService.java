package com.binghetao.service;

import com.binghetao.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}


