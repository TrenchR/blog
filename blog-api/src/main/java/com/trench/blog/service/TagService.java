package com.trench.blog.service;

import com.trench.blog.vo.Result;
import com.trench.blog.vo.TagVo;

import java.util.List;

/**
 * @author Trench
 * @date 2022/3/22
 */
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
