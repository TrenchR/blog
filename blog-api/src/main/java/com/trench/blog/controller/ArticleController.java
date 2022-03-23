package com.trench.blog.controller;

import com.trench.blog.service.ArticleService;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Trench
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticles() {
        int limit = 5;
        return articleService.hotArticles(limit);
    }
}