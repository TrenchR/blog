package com.trench.blog.service;


import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.PageParams;

/**
 * @author Trench
 */
public interface ArticleService {

    /**
     * 分页查询文章流标
     *
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     *
     * @param limit
     * @return
     */
    Result newArticles(int limit);
}
