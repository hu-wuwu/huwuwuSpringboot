package com.huwuwu.learning.configuration.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义延迟交换机
 */
@Configuration
public class DelayedConfig {
    //队列
    public static final String DELAY_QUEUE = "delayedqueue";
    //交换机
    public static final String DELAY_EXCHANGE = "delayedExchange";
    //路由
    public static final String DELAY_ROUTING_KEY = "delayedRouting";

    @Bean
    public Queue delayqueue() {
        return new Queue(DELAY_QUEUE);
    }

    //自定义延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        /**
         * 1、交换机名称
         * 2、交换机类型
         * 3、是否需要持久化
         * 4、是否需要自动删除
         * 5、其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, arguments);
    }

    //绑定队列和延迟交换机
    @Bean
    public Binding delaybinding() {
        return BindingBuilder.bind(delayqueue()).to(delayedExchange()).with(DELAY_ROUTING_KEY).noargs();
    }
}
