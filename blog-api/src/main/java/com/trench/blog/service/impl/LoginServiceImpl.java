package com.trench.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.service.LoginService;
import com.trench.blog.service.SysUserService;
import com.trench.blog.utils.JWTUtils;
import com.trench.blog.vo.ErrorCode;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Trench
 * @date 2022/3/24
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final String salt = "mszlu!@#";

    private SysUserService sysUserService;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Result login(LoginParams loginParams) {
        /*
         * 1 检查参数是否合法；
         * 2 根据用户名和密码去user表中查询。判断是否存在
         * 3 如果不存在：登录失败
         * 4 如果存在：使用jwt，生成token返回给前端
         * 5 将token放入redis中，token：user设置过期时间
         *  （登录认证的时候先认证token字符串是否合法，再认证是否存在）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String pwd = DigestUtils.md5Hex(password + salt);
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),
                    ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = sysUserService.findUser(account, pwd);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),
                    ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /*
         * 1 判断参数是否合法
         * 2 判断账户是否存在。存在：返回账户已经注册
         * 3 如果不存在就注册用户
         * 4 生成token
         * 5 存入redis并返回
         * 6 注意：加上事务，一旦中间出现任何问题，注册的用户需要回滚
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("http://localhost:8080/static/img/test1.png");
        //1 为true
        sysUser.setAdmin(1);
        // 0 为false
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

}