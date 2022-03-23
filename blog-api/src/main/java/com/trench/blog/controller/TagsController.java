package com.trench.blog.controller;

import com.trench.blog.service.TagService;
import com.trench.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trench
 * @date 2022/3/22
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    // Tags/hot
    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }
}