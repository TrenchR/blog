package com.trench.blog.controller;

import com.trench.blog.utils.QiniuUtils;
import com.trench.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/** TODO: 要使用上传图片功能请开启UploadController类上及类里的注解
 * @author Trench
 * @date 2022/3/29
 */
//@RestController
//@RequestMapping("upload")
public class UploadController {

    private QiniuUtils qiniuUtils;

//    @Autowired
    public void setQiniuUtils(QiniuUtils qiniuUtils) {
        this.qiniuUtils = qiniuUtils;
    }

//    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        // 原始文件名称
        String fileName = UUID.randomUUID().toString()
                // 用"."区分
                + "."
                // 取"."后面的字符
                + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001, "上传失败");
    }
}