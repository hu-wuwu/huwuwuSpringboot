package com.huwuwu.learning.jdk8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author huminghao
 */

@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class FunctionInterfaceTest {

    @Test
    public void consumerTest() {
        //消费型--有一个参数没有返回
        Consumer<String> consumer = s -> System.out.println(s);//对这个参数s进行业务操作,注：操作一行代码要用{}

        consumer.accept("消费型接口测试");
    }

    @Test
    public void supplierTest() {
        //供给型--无参返回
        Supplier<Integer> supplier = () -> (int) (Math.random() * 100);
        Integer num = supplier.get();
        System.out.println("供给型返回值为："+ num);
    }

    @Test
    public void functionTest(){
        //函数型--有参有返回
        Function<String,Integer> function = s -> s.length();
        Integer len = function.apply("abcdefg");
        System.out.println("函数式返回的值为："+len);
    }

    @Test
    public void predicateTest(){
        //断言型--有参有返回，返回的事boolean值
        Predicate<Integer> predicate = age -> age>20;
        boolean b = predicate.test(22);
        System.out.println("年龄是否大于20："+ b);
    }

}
