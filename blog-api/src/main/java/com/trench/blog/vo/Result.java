package com.trench.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Trench
 * @date 2022/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(Integer code, String msg) {
        return new Result(false, code, msg, null);
    }
}
