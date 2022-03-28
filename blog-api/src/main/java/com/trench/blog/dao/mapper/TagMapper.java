package com.trench.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trench.blog.dao.pojo.Tag;

import java.util.List;

/**
 * @author Trench
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     *
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签前n条
     *
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 通过tagId查询tag
     *
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
