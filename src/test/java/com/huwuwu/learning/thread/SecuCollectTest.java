package com.huwuwu.learning.thread;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest
public class SecuCollectTest {

    @Test
    public void arrayListTest() throws InterruptedException {
        //创建一个List集合
//        List<Integer> list = new ArrayList<>();
//        List<Integer> list = new Vector<>();//add方法有synchronized修饰，但性能很差，已经淘汰很少使用
        List<Integer> list = new CopyOnWriteArrayList<>();//空间换时间，性能好，但是内存消耗的高

        //创建一个线程集合
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            //创建了5个线程
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    //每个线程在list中添加了10个随机数
                    list.add(new Random().nextInt(100));
                }
            }));
        }

        //启动这5个线程
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();//要等这个五个都执行完才能向下继续执行
        }


        //发现是因为ArrayList集合扩容时出现同时抢占一个位置导致的错误
        System.out.println("list集合中元素有：" + list.size());//应该有50个元素

    }

    @Test
    public void arrayListTest2() throws InterruptedException {
        //创建一个Map集合
//        Map<String,Integer> map = new HashMap<>();
//        Map<String,Integer> map = new Hashtable<>();//和Vector集合一样，都是在put方法上加了synchronized,不推荐
        Map<String,Integer> map = new ConcurrentHashMap<>();//在方法中使用了synchronized

        //创建一个线程集合
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            //创建了5个线程
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    //每个线程在list中添加了10个随机数
                    map.put(UUID.randomUUID().toString(),new Random().nextInt(100));
                }
            }));
        }

        //启动这5个线程
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();//要等这个五个都执行完才能向下继续执行
        }


        //发现是因为HashMap集合扩容时出现同时抢占一个位置导致的错误
        System.out.println("list集合中元素有：" + map.size());//应该有50个元素
    }

}
