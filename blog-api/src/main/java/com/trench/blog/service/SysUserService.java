package com.trench.blog.service;

import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.UserVo;

/**
 * @author Trench
 * @date 2022/3/22
 */
public interface SysUserService {

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return SysUser
     */
    SysUser findUserById(Long id);

    /**
     * 查找用户名和密码
     *
     * @param account
     * @param password
     * @return SysUser
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return Result
     */
    Result getUserInfoByToken(String token);

    /**
     * 根据账户名查找用户
     *
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     *
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据id查询用户信息vo
     *
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);

}
