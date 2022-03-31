package com.trench.blog.admin.model.params;

import lombok.Data;

/**
 * @author Trench
 */
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
