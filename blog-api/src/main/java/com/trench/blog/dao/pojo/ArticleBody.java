package com.trench.blog.dao.pojo;

import lombok.Data;

/**
 * @author Trench
 * @date 2022/3/27
 */

@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;

}