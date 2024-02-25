package com.huwuwu.learning.demos.service.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author huminghao
 */
@Component
public class Task2 implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在进行求和运算");
        int sum = 0;
        for (int i = 1; i <= 100000; i++) {
            sum+=i;
        }
        Thread.sleep(3000);
        return sum;
    }
}
