package com.huwuwu.learning.demos.service.thread;

import org.springframework.stereotype.Component;

/**
 * @author huminghao
 */
//不是线程类，是线程任务类
@Component
public class Task implements Runnable{

    @Override
    public void run() {
        System.out.println("子线程创建的第二种方式");
    }
}
