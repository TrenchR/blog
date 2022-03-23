package com.trench.blog.service;

import com.trench.blog.dao.pojo.SysUser;

/**
 * @author Trench
 * @date 2022/3/22
 */
public interface SysUserService {

    SysUser findUserById(Long id);
}
