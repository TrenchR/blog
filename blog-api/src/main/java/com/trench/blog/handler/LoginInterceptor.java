package com.trench.blog.handler;

import com.alibaba.fastjson.JSON;
import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.service.LoginService;
import com.trench.blog.utils.UserThreadLocal;
import com.trench.blog.vo.ErrorCode;
import com.trench.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Trench
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 在执行controller方法（Handler）之前进行执行
         * 1 需要判断请求接口路径是否是HandlerMethod（Controller方法）
         * 2 判断token是否为空，如果为空标示未登录
         * 3 如果token不为空，登录验证loginService checkToken
         * 4 如果认证成功，则放行
         * */
        if (!(handler instanceof HandlerMethod)) {
            // handler可能是RequestResourceHandler。SpringBoot程序访问静态支援默认去classpath下的static目录去访问
            return true;
        }

        String token = request.getHeader("Authorization");
        // 打开log显示用户登录信息
//        log.info("=================request start===========================");
//        String requestURI = request.getRequestURI();
//        log.info("request uri:{}", requestURI);
//        log.info("request method:{}", request.getMethod());
//        log.info("token:{}", token);
//        log.info("=================request end===========================");

        if (token == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 删除UserThreadLocal。如果不删除信息会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
