package com.trench.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trench.blog.dao.doc.Archives;
import com.trench.blog.dao.mapper.ArticleBodyMapper;
import com.trench.blog.dao.mapper.ArticleMapper;
import com.trench.blog.dao.mapper.CategoryMapper;
import com.trench.blog.dao.pojo.Article;
import com.trench.blog.dao.pojo.ArticleBody;
import com.trench.blog.service.*;
import com.trench.blog.vo.ArticleBodyVo;
import com.trench.blog.vo.ArticleVo;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Trench
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleMapper articleMapper;
    private ArticleBodyMapper articleBodyMapper;
    private CategoryMapper categoryMapper;

    private SysUserService sysUserService;

    private TagService tagService;

    private CategoryService categoryService;

    private ThreadService threadService;


    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setArticleBodyMapper(ArticleBodyMapper articleBodyMapper) {
        this.articleBodyMapper = articleBodyMapper;
    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @Override
    public Result listArticle(PageParams pageParams) {
        // 分页查询article数据库表
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate, Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // sql语句：select id,title from article order by view_counts desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts);
        /*
         * 下面语句会造成前端显示问题，参数不匹配。保留以便以后检查
         * queryWrapper.select(Article::getId,Article::getTitle);
         */
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        /*
         * 下面语句会造成前端显示问题，参数不匹配
         * queryWrapper.select(Article::getId, Article::getTitle);
         */
        queryWrapper.last("limit " + limit);
        // 执行sql语句：select id,title from article order by creat_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public ArticleVo findArticleById(Long articleId) {
        /*
         * 1 根据id查询文章信息
         * 2 根据bodyId和categoryId关联查询
         */
        Article article = articleMapper.selectById(articleId);
        /*
         * 问题：查看文章完成后添加更新操作，此时会添加写锁，要阻碍其他的操作，性能就会降低
         * 需要优化：更新出问题不能查看文章的操作
         * 解决方法：使用线程池，可以吧更新操作扔到线程池中执行，和主线程不相关了
         */
        threadService.updateViewCount(articleMapper, article);
        return copy(article, true, true, true, true);
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,
                                     boolean isTag, boolean isAuthor,
                                     boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            articleVoList.add(copy(article, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    public ArticleVo copy(Article article,
                          boolean isTag, boolean isAuthor,
                          boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 不是所有接口都需要标签和作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

}