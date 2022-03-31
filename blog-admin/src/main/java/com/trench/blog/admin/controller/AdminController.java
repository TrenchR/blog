package com.trench.blog.admin.controller;

import com.trench.blog.admin.model.params.PageParam;
import com.trench.blog.admin.pojo.Permission;
import com.trench.blog.admin.service.PermissionService;
import com.trench.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Trench
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    private PermissionService permissionService;

    @Autowired
    public  void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
