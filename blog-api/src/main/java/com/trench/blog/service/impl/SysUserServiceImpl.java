package com.trench.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trench.blog.dao.mapper.SysUserMapper;
import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.service.SysUserService;
import com.trench.blog.utils.JWTUtils;
import com.trench.blog.vo.ErrorCode;
import com.trench.blog.vo.LoginUserVo;
import com.trench.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Trench
 * @date 2022/3/22
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper,
                              RedisTemplate<String, String> redisTemplate) {
        this.sysUserMapper = sysUserMapper;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("aka");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount,
                            SysUser::getId,
                            SysUser::getAvatar,
                            SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result getUserInfoByToken(String token) {
        /*
         * 1 合法性校验：是否为空，解析是否成功，redis是够存在
         * 2 如果校验失败，返回错误
         * 3 如果成功，返回对应结果 LoginUserVo
         */
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

}