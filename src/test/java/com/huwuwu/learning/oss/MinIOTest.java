package com.huwuwu.learning.oss;

import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.commons.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class MinIOTest {

    @Autowired
    private MinioUtil minioUtil;

    @Test
    public void Test1() throws Exception {
        boolean flag = minioUtil.existBucket("test");
        String objectUrl = null;
        if (flag){
            objectUrl = minioUtil.getPreSignUrl("man.png");
        }
        System.out.println(objectUrl);



    }

}
