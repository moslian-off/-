package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename != null && !filename.isEmpty()) {
            int lastDot = filename.lastIndexOf(".");
            String extName = filename.substring(lastDot);
            String originName = filename.substring(0, lastDot);
            String newName = originName + UUID.randomUUID() + extName;
            return Result.success(aliOssUtil.upload(file.getBytes(), newName));
        } else return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
