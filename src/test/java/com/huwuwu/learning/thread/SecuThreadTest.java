package com.huwuwu.learning.thread;

import com.huwuwu.learning.demos.service.thread.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

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

    //在内部类中访问当前方法局部变量一定要是final修饰的常量，不能修改，所以放在方法外面
    int i=0;

    @Test
    public void SecuThreadTest3() throws InterruptedException {

        Thread t1 = new Thread(() -> {
        //在内部类中访问当前方法局部变量一定要是final修饰的常量
            for (int j = 0; j < 1000; j++) {
                synchronized (System.out){
                    i++;//从多线程的角度来说分为四个步骤
                }

            }
        });

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                synchronized (System.out){
                    i--;
                }

            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("i的值为："+i);//我们希望是0
    }

    @Test
    public void SecuThreadTest4() throws InterruptedException {

        Account a1 = new Account(1000);
        Account a2 = new Account(1000);

        Random random = new Random();

        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                a1.transfer2(a2,random.nextInt(10));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                a2.transfer2(a1,random.nextInt(10));
            }
        });


        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("账户总额："+(a1.getBalance()+a2.getBalance()));
    }


}

