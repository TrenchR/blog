package com.trench.blog.controller;

import com.trench.blog.common.aop.LogAnnotation;
import com.trench.blog.common.cache.Cache;
import com.trench.blog.service.ArticleService;
import com.trench.blog.vo.ArticleVo;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.ArticleParam;
import com.trench.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Trench
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 首页文章列表
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hotArticle")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     *
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000,name = "newArticles")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     *
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }


    /**
     * 查看文章详情
     *
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return Result.success(articleService.findArticleById(articleId));
    }

    /**
     * 发布文章
     *
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

}