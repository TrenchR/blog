package com.trench.blog.dao.pojo;

import lombok.Data;

/**
 * @author Trench
 * @date 2022/3/27
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;

}