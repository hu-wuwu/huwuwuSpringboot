package com.huwuwu.learning.demos.service.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huminghao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account{

    /**
     * 账户余额
     */
    private int balance;

    //对外提供一个取款的方法
    public void withDraw(int money){
        String thName = Thread.currentThread().getName();
        if (this.balance >= money){
            System.out.println(thName+"取款"+money +"成功，正在吐钞。。");
            this.balance -= money;
        }else{
            System.out.println("账户余额不足！！");
        }
    }

}
