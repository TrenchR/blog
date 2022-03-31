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
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        // sso 单点登录，后期如果把登录注册功能提取出去
        return loginService.register(loginParams);
    }

}