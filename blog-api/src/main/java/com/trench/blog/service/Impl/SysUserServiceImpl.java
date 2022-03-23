package com.trench.blog.service.Impl;

import com.trench.blog.dao.mapper.SysUserMapper;
import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Trench
 * @date 2022/3/22
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("aka");
        }
        return sysUser;
    }
}