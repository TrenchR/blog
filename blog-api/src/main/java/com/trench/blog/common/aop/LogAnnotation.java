package com.trench.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author Trench
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
