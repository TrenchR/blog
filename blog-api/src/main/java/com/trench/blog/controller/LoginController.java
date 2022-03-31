package com.trench.blog.controller;

import com.trench.blog.service.LoginService;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trench
 * @date 2022/3/24
 */
@RestController
@RequestMapping("login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        // 登录要验证用户，访问用户表
        return loginService.login(loginParams);
    }
}