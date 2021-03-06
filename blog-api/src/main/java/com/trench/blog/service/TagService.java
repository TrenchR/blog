package com.trench.blog.service;

import com.trench.blog.vo.Result;
import com.trench.blog.vo.TagVo;

import java.util.List;

/**
 * @author Trench
 * @date 2022/3/22
 */
public interface TagService {
    /**
     * 查找文章标签id
     *
     * @param articleId
     * @return List<TagVo>
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查找最热文章
     *
     * @param limit
     * @return Result
     */
    Result hots(int limit);

    /**
     * 查询所有文章标签
     *
     * @return
     */
    Result findAll();

    /**
     * 查找所有tag的详情
     *
     * @return
     */
    Result findAllDetail();

    /**
     * 文章列表通过id查找详情
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}