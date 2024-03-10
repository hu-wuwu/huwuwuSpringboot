package com.huwuwu.learning.encoder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huminghao
 */
@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class BCryptPasswordEncoderTest {

    @Test
    public void encodeTest1(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void matches(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean flag = encoder.matches("123456", "$2a$10$wKsKDR/PLSWsgLhiB2H1jOpOHqXpplaS89ATfuOs1q36aaYzjDiVe");
        System.out.println("是否匹配成功："+flag);
    }

}
