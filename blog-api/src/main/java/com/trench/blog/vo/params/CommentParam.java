package com.trench.blog.vo.params;

import lombok.Data;

/**
 * @author Trench
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
