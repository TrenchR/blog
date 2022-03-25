package com.trench.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Trench
 */
@Data
public class SysUser {

    /*
     * @TableId(type = IdType.ASSIGN_ID)
     * 以后用户多了后要进行分表操作，id需要分布式id
     */

    @TableId(type = IdType.AUTO)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
