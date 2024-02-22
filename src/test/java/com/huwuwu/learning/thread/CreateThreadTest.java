package com.huwuwu.learning.thread;


import com.huwuwu.learning.demos.service.thread.MyThread1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class CreateThreadTest{

    @Test
    public void threadTest1(){
        //1、创建线程对象
        MyThread1 myThread1 = new MyThread1();
        //2、调用线程对象的start方法将当前线程加入到cpu的执行队列
        //CPU具体什么时间去处理新任务不归我们控制
        //Java中多线程对CPU的执行权采取的是抢占式的
        myThread1.start();//启动线程
        //不要自己去调用线程对象的run方法，这个方法是给CPU来调用的
        // 我们只负责设计这个任务过程并加入到任务队列即可
//        myThread1.run();//这是自己调run方法，还是主线程
        System.out.println("main线程继续执行。。。");


    }


}
