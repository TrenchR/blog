package com.trench.blog.service;

import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.vo.Result;

/**
 * @author Trench
 * @date 2022/3/22
 */
public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result getUserInfoByToken(String token);
}
