package com.huwuwu.learning.thread;

import com.huwuwu.learning.demos.service.thread.Monitor;
import com.huwuwu.learning.demos.service.thread.Monitor2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class ThreadMethodTest {//子线程的常用方法

    @Test
    public void ThreadMethodTest1() throws InterruptedException {
        //join方法
        Thread t1 = new Thread(() -> {
            System.out.println("子线程1执行。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程1执行结束");
        });

//        t1.start();

        System.out.println("main。。。");

        //需求：要求执行要部分业务后t1线程执行完了才继续往下执行
//        t1.join();//等待t1线程执行完--死等
//        t1.join(3000);//设置最大的等待超时时间


        //需求：提前中断线程--推荐使用interrupt
//        t1.stop();//不安全-不推荐使用

        Monitor monitor = new Monitor();
        Thread thread = new Thread(monitor);
        thread.start();

        Thread.sleep(1000);
        System.out.println("main线程即将结束。。。");


        thread.interrupt();//给线程发一个中断的通知

    }

    @Test
    public void ThreadMethodTest2() throws InterruptedException {
        Thread tt = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("tt线程即将结束");
        });
        tt.start();

        Monitor2 monitor = new Monitor2();
        Thread thread = new Thread(monitor);
        //将该线程设置为守护线程
        thread.setDaemon(true);
        thread.start();

        System.out.println("主线程继续执行。。");

        Thread.sleep(5000);

        System.out.println("主线程即将结束。。");

    }

}
