package com.huwuwu.learning.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huminghao
 */
@Slf4j
@Configuration
public class DeadLetterConfig {

    //队列
    public static final String DEAD_LETTER_QUEUE = "deadLetterdqueue";
    //交换机
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";
    //路由
    public static final String DEAD_LETTER_ROUTING_KEY = "deadLetterRouting";

    /**
     * 转发到 死信队列，配置参数
     */
    public static Map<String, Object> deadQueueArgs() {
        Map<String, Object> map = new HashMap<>();
        // 绑定该队列到死信交换机
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return map;
    }


    // 声明死信队列A
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    // 声明死信Exchange
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    // 声明死信队列A绑定关系
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }
}
