package com.huwuwu.learning.thread;


import com.huwuwu.learning.demos.service.thread.MyThread1;
import com.huwuwu.learning.demos.service.thread.Task;
import com.huwuwu.learning.demos.service.thread.Task2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
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

    @Test
    public void threadTest2(){
        //第二种方法
        //1、创建一个线程任务对象
        Task task = new Task();
        //2、创建一个线程对象
        Thread thread = new Thread(task);
        //3、启动线程
        thread.start();
    }

    @Test
    public void threadTest2_1(){
        //第二种方法--使用lambda方式
        //1、创建一个线程任务对象
        //2、创建一个线程对象
        //3、启动线程
        new Thread(()-> System.out.println("lambda表达式实现第二种创建线程写法！！！")).start();

    }

    @Test
    public void threadTest3() throws ExecutionException, InterruptedException {
        //第三种方式
        //1、创建任务对象
        Task2 task2 = new Task2();//Callable接口的任务

        //2、将Callable接口的任务对象包装成Runnable接口的任务对象
        //创建一个未来任务类对象
        FutureTask<Integer> futureTask = new FutureTask<>(task2);

        //3、创建线程对象
        Thread thread = new Thread(futureTask);

        //匿名内部类
        Thread t_nbl = new Thread(new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return (int) (Math.random() * 100);
            }
        }));


        //lambda
        Thread t_lambda = new Thread(new FutureTask<Integer>(()->  (int) (Math.random() * 100)));

        //4、启动线程
        thread.start();

        //5、在主线程中获取子线程返回值--》注：子线程可能还没执行完
        System.out.println("main线程继续执行。。");

        Integer res = futureTask.get();//发现主线程一定会等待子线程执行完【死等】，原因：get方法是一个阻塞的方法

        System.out.println("子线程返回值"+res);



    }

}
