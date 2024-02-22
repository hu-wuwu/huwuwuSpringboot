package com.huwuwu.learning.log;


import com.esotericsoftware.minlog.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class LoggingTest {

    @Test
    public void LoggingTest1(){
        log.trace("trace级别的日志输出");
        log.debug("debug级别的日志输出");
        log.info("info级别的日志输出");
        log.error("error级别的日志输出");
    }


}
