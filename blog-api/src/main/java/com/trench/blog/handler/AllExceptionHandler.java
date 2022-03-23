package com.trench.blog.handler;

import com.trench.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Trench
 * @date 2022/3/22
 */

@ControllerAdvice   // 对@Controller注解的方法进行拦截处理，AOP实现
public class AllExceptionHandler {
    /*
    * 进行异常处理，处理Exception.class的异常
    */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}