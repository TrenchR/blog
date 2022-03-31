package com.trench.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trench.blog.admin.mapper.AdminMapper;
import com.trench.blog.admin.mapper.PermissionMapper;
import com.trench.blog.admin.pojo.Admin;
import com.trench.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Trench
 */
@Service
public class AdminService {

    private AdminMapper adminMapper;

    private PermissionMapper permissionMapper;

    @Autowired
    public void setAdminMapper (AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public Admin findAdminByUserName(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username).last("limit 1");
        Admin adminUser = adminMapper.selectOne(queryWrapper);
        return adminUser;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId){
        return permissionMapper.findPermissionsByAdminId(adminId);
    }

}
