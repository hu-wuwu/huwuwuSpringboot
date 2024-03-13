package com.huwuwu.learning.web;

import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.commons.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@Slf4j
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;

    @Value("${minio.endpoint}")
    private String address;

    @Value("${minio.bucketName}")
    private String bucketName;

    @PostMapping("/upload")
    public BaseResponse upload(MultipartFile file) {
        List<String> upload = minioUtil.upload(new MultipartFile[]{file});
        return ResultUtils.success("上传成功！");
    }

    @PostMapping("/getFileUrl")
    public BaseResponse getFileUrl(String fileName) throws Exception{
        String fileUrl = minioUtil.getPreSignUrl(fileName);
        return ResultUtils.success(fileUrl);
    }





}


