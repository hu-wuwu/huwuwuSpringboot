package com.huwuwu.learning.demos.service.thread;

import io.reactivex.rxjava3.functions.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintStream;
import java.lang.annotation.Target;

/**
 * @author huminghao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account{

    private static final Object lock = new Object();

    /**
     * 账户余额
     */
    private int balance;

    //对外提供一个取款的方法
    public void withDraw(int money){
        String thName = Thread.currentThread().getName();
        //多线程对共享数据的读写代码【临界区】
        synchronized (lock){
            if (this.balance >= money){
                System.out.println(thName+"取款"+money +"成功，正在吐钞。。");
                this.balance -= money;
            }else{
                System.out.println("账户余额不足！！");
            }
        }

    }

    //对外提供一个取款的方法
    //synchronized作为方法的修饰词，默认使用this作为锁对象，此时的锁对象是调用者，
    public synchronized void withDraw2(int money){
        String thName = Thread.currentThread().getName();
        //多线程对共享数据的读写代码【临界区】
        if (this.balance >= money){
            System.out.println(thName+"取款"+money +"成功，正在吐钞。。");
            this.balance -= money;
        }else{
            System.out.println("账户余额不足！！");
        }

    }

    /**
     * 转账
     */
    public void transfer(Account target,int money){
        //多线程对共享数据的读写代码【临界区】
        //System.out返回PrintStream对象，这是static修饰的对象，全局唯一
        synchronized (System.out) {
            if (this.balance >= money) {
                this.balance -= money;
                target.balance += money;
            }
        }

    }

    /**
     * 注：synchronized作为方法的修饰词，默认使用this作为锁对象，此时的锁对象是调用者
     */

    public synchronized void transfer2(Account target,int money){
        //多线程对共享数据的读写代码【临界区】
        if (this.balance >= money) {
            this.balance -= money;
            target.balance += money;
        }
    }

    /**
     * 注意：静态方法的synchronized的锁对象是当前类的字节码，例：Account.class
     * @param target
     * @param money
     */
    public static synchronized void test(Account target,int money){
        //多线程对共享数据的读写代码【临界区】

    }

}
