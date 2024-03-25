package com.huwuwu.learning.configuration.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    public static final String FANOUT_QUEUE_NAME = "htFanoutQueue";
    public static final String FANOUT_EXCHANGE_NAME = "htFanoutExchange";

    //发送消息时只需要创建exchange即可
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public Queue fanoutQueue() {
        //durable:true mq服务器重启后仍然存在 false重启后队列自动删除
        return new Queue(FANOUT_QUEUE_NAME, true);
    }

    /**
     * 队列绑定fanout类型的exchange是无法设置routingKey
     *
     * @return
     */
    @Bean
    Binding bindingFanout() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
}
