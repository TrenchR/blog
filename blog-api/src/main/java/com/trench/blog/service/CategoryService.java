package com.trench.blog.service;

import com.trench.blog.vo.CategoryVo;
import com.trench.blog.vo.Result;

/**
 * @author Trench
 * @date 2022/3/27
 */
public interface CategoryService {
    /**
     * 查询类别的id
     *
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查找所有的分类
     *
     * @return
     */
    Result findAll();

    /**
     * 查找所有的分类详情
     *
     * @return
     */
    Result findAllDetail();

    /**
     * 文章分类列表
     *
     * @param id
     * @return
     */
    Result categoriesDetailById(Long id);
}
