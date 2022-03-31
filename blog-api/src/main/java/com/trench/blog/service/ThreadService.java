package com.trench.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trench.blog.dao.mapper.ArticleMapper;
import com.trench.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Trench
 */
@Component
public class ThreadService {

    /**
     * 执行线程池，避免影响主线程
     *
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, queryWrapper);
//        try {
//            // 睡眠5秒 证明不会影响主线程的使用
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}