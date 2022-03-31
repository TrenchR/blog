package com.trench.blog.vo.params;

import com.trench.blog.vo.CategoryVo;
import com.trench.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author Trench
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
