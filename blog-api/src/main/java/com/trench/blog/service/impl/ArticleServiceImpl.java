package com.trench.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trench.blog.dao.doc.Archives;
import com.trench.blog.dao.mapper.ArticleBodyMapper;
import com.trench.blog.dao.mapper.ArticleMapper;
import com.trench.blog.dao.mapper.ArticleTagMapper;
import com.trench.blog.dao.mapper.CategoryMapper;
import com.trench.blog.dao.pojo.Article;
import com.trench.blog.dao.pojo.ArticleBody;
import com.trench.blog.dao.pojo.ArticleTag;
import com.trench.blog.dao.pojo.SysUser;
import com.trench.blog.service.*;
import com.trench.blog.utils.UserThreadLocal;
import com.trench.blog.vo.ArticleBodyVo;
import com.trench.blog.vo.ArticleVo;
import com.trench.blog.vo.Result;
import com.trench.blog.vo.TagVo;
import com.trench.blog.vo.params.ArticleParam;
import com.trench.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ArticleTagMapper articleTagMapper;

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
    public void setArticleTagMapper(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
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
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }

//    @Override
//    public Result listArticle(PageParams pageParams) {
//        // ????????????article????????????
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        // ????????????????????? ????????????id?????????????????? ??????????????????
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0) {
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//        queryWrapper.orderByDesc(Article::getCreateDate, Article::getWeight);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records, true, true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // sql?????????select id,title from article order by view_counts desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts);
        /*
         * ????????????????????????????????????????????????????????????????????????????????????
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
         * ?????????????????????????????????????????????????????????
         * queryWrapper.select(Article::getId, Article::getTitle);
         */
        queryWrapper.last("limit " + limit);
        // ??????sql?????????select id,title from article order by creat_date desc limit 5
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
         * 1 ??????id??????????????????
         * 2 ??????bodyId???categoryId????????????
         */
        Article article = articleMapper.selectById(articleId);
        /*
         * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????????????????????????????????????????????????????
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

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        // ??????????????????????????????????????????
        SysUser sysUser = UserThreadLocal.get();

        /*
         * 1. ?????????????????????Article??????
         * 2. ??????id????????????????????????
         * 3. ?????????????????????????????????
         * 4. ????????????, article bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        this.articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }

        // Body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));

        return Result.success(articleVo);
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
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // ????????????????????????????????????????????????
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