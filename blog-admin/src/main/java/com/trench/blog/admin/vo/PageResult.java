package com.trench.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Trench
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;

}
