package com.trench.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trench.blog.dao.doc.Archives;
import com.trench.blog.dao.pojo.Article;

import java.util.List;

/**
 * @author Trench
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();
}
