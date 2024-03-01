package com.huwuwu.learning.thread;

import com.sun.rowset.internal.Row;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 线程通信测试
 */
@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest
public class ThreadCommTest {

    @Test
    public void ThreadCommTest1() throws InterruptedException {
        /**
         * wait、notify、notifyall这是三个方法一定要写在同步代码块（锁住的代码块）中【这三个方法的调用需要获取到对象锁】，否则会非法监视器异常
         */
        //创建一个锁对象【空对象】
        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("子线程准备进入等待。。");
                    //wait：让调用这个方法的线程进入等待【阻塞】
                    //与sleep不一样，wait的同时会立马释放锁，sleep不会
                    lock.wait(3000);//无参默认为0，表示死等
//                    Thread.sleep(3000);
//                    System.out.println("子线程继续执行。。");
                } catch (InterruptedException e) {
//            e.printStackTrace();
                    System.out.println("wait被打断异常。");
                }
                System.out.println("子线程即将结束。");

            }
        });

        t1.start();
        Thread.sleep(10);

        for (int i = 0; i < 5; i++) {
            synchronized (lock) {
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在执行");
                }).start();
            }
        }


//        Thread.sleep(3000);

        boolean alive = t1.isAlive();
        if (alive) {
            System.out.println("主线程准备打断子线程。。");
            //非正常打断等待
            t1.interrupt();
        } else {
            System.out.println("t1子线程执行结束。写·");
        }

        System.out.println("main方法即将结束。。");

    }

    @Test
    public void ThreadCommTest2() throws InterruptedException {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (lock) {
                System.out.println("t1子线程即将进行等待。。");
                try {
//                    lock.notify();
                    lock.wait();
                    System.out.println("t1被唤醒。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("t1子线程被打断。");
                }
                System.out.println("t1子线程即将结束。");
            }


        });
        Thread t2 = new Thread(() -> {

            synchronized (lock) {
                System.out.println("t2子线程即将进行等待。。");
                try {
//                    lock.notify();
                    lock.wait();
                    System.out.println("t2被唤醒。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("t2子线程被打断。");
                }
                System.out.println("t2子线程即将结束。");
            }


        });

        t1.start();
        t2.start();
        Thread.sleep(3000);

        synchronized (lock) {
//            lock.notify();//这个方法也要在同步块（锁住的代码块）中--随机唤醒使用该锁的子线程
            lock.notifyAll();//唤醒所有线程
        }
    }

    static Object room = new Object();
    static boolean hasYan = false;
    static boolean hasJiu = false;

    @Test
    public void ThreadCommTest3() throws InterruptedException {

        Thread t1 = new Thread(() -> {

            synchronized (room) {
                System.out.println("张三进入到房间准备干活");
                while (!hasYan) {
                    try {
                        System.out.println("没有烟，张三进入等待。。。");
                        room.wait();//立马释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("张三抽口烟，继续干活。。。");
            }
        });

        Thread t2 = new Thread(() -> {

            synchronized (room) {
                System.out.println("李四进入到房间准备干活");
                while (!hasJiu) {
                    try {
                        System.out.println("没有酒，李四进入等待。。。");
                        room.wait();//立马释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("李四喝口酒，继续干活。。。");
            }
        });

        Thread t3 = new Thread(() -> {

            synchronized (room) {
                hasYan = true;
                System.out.println("烟已送到，准备唤醒等烟的线程。。。");
                //要求精确唤醒张三
                room.notifyAll();
            }
        });
        Thread t4 = new Thread(() -> {

            synchronized (room) {
                hasJiu = true;
                System.out.println("酒已送到，准备唤醒等酒的线程。。。");
                //要求精确唤醒李四
                room.notifyAll();
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(3000);
        t3.start();
        t4.start();

    }
}
