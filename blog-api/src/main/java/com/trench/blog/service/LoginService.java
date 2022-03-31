package com.trench.blog.service;

import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.LoginParams;

/**
 * @author Trench
 * @date 2022/3/24
 */
public interface LoginService {
    /**
     * 登录功能
     *
     * @param loginParams
     * @return Result
     */
    Result login(LoginParams loginParams);

    /**
     * 注销登录
     *
     * @param token
     * @return Result
     */
    Result logout(String token);

    /**
     * 注册
     *
     * @param loginParams
     * @return Result
     */
    Result register(LoginParams loginParams);

    /**
     * 检查token
     *
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
