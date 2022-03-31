package com.trench.blog.vo.params;

import lombok.Data;

/**
 * @author Trench
 * @date 2022/3/24
 */
@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;

}