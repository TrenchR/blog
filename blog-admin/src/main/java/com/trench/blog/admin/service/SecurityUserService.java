package com.trench.blog.admin.service;

import com.trench.blog.admin.pojo.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class SecurityUserService implements UserDetailsService {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         * 1 登录的时候会把username传递到这
         * 2 通过username查询admin表，如果admin存在，将密码告诉spring security
         * 3 如果不存在返回null，认证失败
         */
        log.info("username:{}", username);
        //当用户登录的时候，springSecurity 就会将请求 转发到此
        //根据用户名查找用户，不存在 抛出异常，存在 将用户名、密码、授权列表组装成springSecurity的User对象并返回
        Admin adminUser = adminService.findAdminByUserName(username);
        if (adminUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User(username, adminUser.getPassword(), authorities);
        //剩下的认证 就由框架帮我们完成
        return userDetails;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
