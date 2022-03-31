package com.trench.blog.vo;

/**
 * @author Trench
 */

public enum ErrorCode {

    // 参数有误
    PARAMS_ERROR(10001, "参数有误"),
    // 用户名或密码不存在
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),
    // 用户名存在
    ACCOUNT_EXIST(10003, "用户名存在"),
    // token不合法
    TOKEN_ERROR(10004, "token不合法"),
    // 无访问权限
    NO_PERMISSION(70001, "无访问权限"),
    // 会话超时
    SESSION_TIME_OUT(90001, "会话超时"),
    // 未登录
    NO_LOGIN(90002, "未登录"),
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
