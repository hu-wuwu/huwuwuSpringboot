package com.huwuwu.learning.demos.service.thread;

import org.springframework.stereotype.Service;

@Service
public class MyThread1 extends Thread{


    @Override
    public void run() {
        System.out.println("子线程开始处理任务");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
