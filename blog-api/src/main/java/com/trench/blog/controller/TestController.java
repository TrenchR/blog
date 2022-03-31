package com.trench.blog.controller;

import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.utils.UserThreadLocal;
import com.trench.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trench
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test() {
        SysUser sysUser = UserThreadLocal.get();
        System.out.println("----------UserThreadLocal----------");
        System.out.println(sysUser);
        return Result.success(null);
    }
}
