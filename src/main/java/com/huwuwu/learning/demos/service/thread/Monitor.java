package com.huwuwu.learning.demos.service.thread;

public class Monitor implements Runnable{
    @Override
    public void run() {

        //获取执行当前任务的当前线程对象
        Thread thread = Thread.currentThread();

        while (true){
            boolean interrupted = thread.isInterrupted();
            System.out.println(interrupted);
            if(interrupted){
                System.out.println("接收到外界的中断通知，准备退出...");
                break;
            }
            System.out.println("正在监控系统的运行状态");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("监控睡眠被打断");
                thread.interrupt();//让isInterrupted标记置为true
            }

        }

    }
}
