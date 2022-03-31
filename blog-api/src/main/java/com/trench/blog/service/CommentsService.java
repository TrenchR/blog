package com.trench.blog.service;

import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.CommentParam;

/**
 * @author Trench
 */
public interface CommentsService {

    /**
     * 根据文章id查询所有的评论列表
     *
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 写评论
     *
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
