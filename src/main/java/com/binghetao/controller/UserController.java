package com.binghetao.controller;

import com.binghetao.pojo.Result;
import com.binghetao.pojo.User;
import com.binghetao.service.UserService;
import com.binghetao.utils.JwtUtil;
import com.binghetao.utils.Md5Util;
import com.binghetao.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        // 查找用户
        User u = userService.findByUserName(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户名被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User u = userService.findByUserName(username);
        if (u == null) {
            return Result.error("用户名不正确");
        }
        if(Md5Util.getMD5String(password).equals(u.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", u.getUsername());
            claims.put("id", u.getId());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public void update(@RequestBody @Validated User user) {
        userService.update(user);
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        // 参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)
                || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }

        // 校验原密码
        Map<String, Object>map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if(!Md5Util.getMD5String(oldPwd).equals(loginUser.getPassword())) {
            return Result.error("原密码不正确");
        }

        // 新密码和确认密码是否一样
        if(!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }
        // 更新密码
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
