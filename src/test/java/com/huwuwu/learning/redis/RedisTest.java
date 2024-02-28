package com.huwuwu.learning.redis;


import com.huwuwu.learning.commons.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void redisTest1(){

        redisUtil.set("aaa","100");

    }

    @Test
    public void redisTest2() throws InterruptedException {
        redisUtil.expire("aaa",10);

        Thread.sleep(10001);

        Object aaa = redisUtil.get("aaa");
        System.out.println(aaa);

    }

}
