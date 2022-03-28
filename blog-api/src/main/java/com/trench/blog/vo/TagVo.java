package com.trench.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Trench
 */
@Data
public class TagVo {

    // 原来是String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String tagName;

//    private String avatar;
}
