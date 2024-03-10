package com.huwuwu.learning.encoder;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huminghao
 */

@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class BasicTextEncryptorTest {


    @Test
    public void StringEncryptorTest1(){
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // 加密秘钥（盐值）
        basicTextEncryptor.setPassword("salt");

        // 对账号加密
        String encUsername = basicTextEncryptor.encrypt("root");
        System.out.println(encUsername);

        // 对密码加密
        String encPassword = basicTextEncryptor.encrypt("123456");
        System.out.println(encPassword);
    }


    @Test
    public void decrypt() {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // 加密秘钥（盐值）
        basicTextEncryptor.setPassword("salt");
        // 解密  输入获取的加密字符串进行解密
        String dusernameEcrypt = basicTextEncryptor.decrypt("nqul0gb/CJT8jeAwspvHGg==");
        String passwordEcrypt = basicTextEncryptor.decrypt("8z+KoyPEbdH6TM6B9Eh1XQ==");
        System.out.println("dusernameEcrypt = " + dusernameEcrypt);
        System.out.println("passwordEcrypt = " + passwordEcrypt);
    }
}
