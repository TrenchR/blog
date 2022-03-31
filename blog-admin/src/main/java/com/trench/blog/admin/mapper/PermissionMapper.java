package com.trench.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trench.blog.admin.pojo.Permission;

import java.util.List;

/**
 * @author Trench
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionsByAdminId(Long adminId);
}
