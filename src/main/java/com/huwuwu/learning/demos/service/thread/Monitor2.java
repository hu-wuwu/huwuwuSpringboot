package com.huwuwu.learning.demos.service.thread;

public class Monitor2 implements Runnable{
    @Override
    public void run() {

        while (true){

            System.out.println("监控系统的运行中。。");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("监控睡眠被打断");
            }
            System.out.println("正在保存监控数据。。。");

        }


    }
}
