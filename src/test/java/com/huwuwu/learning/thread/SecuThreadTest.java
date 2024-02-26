package com.huwuwu.learning.thread;

import com.huwuwu.learning.demos.service.thread.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huminghao
 */
@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest
public class SecuThreadTest {

    @Test
    public void SecuThreadTest1(){
        //创建一个账户对象
        Account account = new Account(1000);

        //需求：使用单线程实现两次取款
        account.withDraw(600);
        System.out.println("账户余额："+account.getBalance());

        account.withDraw(600);
        System.out.println("账户余额："+account.getBalance());

    }

    @Test
    public void SecuThreadTest2(){
        //创建一个账户对象
        Account account = new Account(1000);

        //需求：使用多线程实现两次取款

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withDraw(600);
            }
        }, "张三");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withDraw(600);
            }
        }, "张三老婆");

        t1.start();
        t2.start();

        System.out.println("账户余额："+account.getBalance());
    }

}

