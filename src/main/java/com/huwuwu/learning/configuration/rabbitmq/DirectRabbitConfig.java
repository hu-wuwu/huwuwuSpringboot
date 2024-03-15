package com.huwuwu.learning.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GXM
 * @version 1.0.0
 * @Description 创建direct类型的交换机
 * @createTime 2023年01月03日
 */
@Slf4j
@Configuration
public class DirectRabbitConfig {


    public static final String DIRECT_QUEUE = "TestDirectQueue";
    public static final String DIRECT_EXCHANGE = "TestDirectExchange";
    public static final String DIRECT_ROUTING_KEY = "TestDirectRouting";

    /**
     * 创建一个名为TestDirectQueue的队列
     *
     * @return
     */
    @Bean
    public Queue directQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，有消息者订阅本队列，然后所有消费者都解除订阅此队列，会自动删除。
        // arguments：队列携带的参数，比如设置队列的死信队列，消息的过期时间等等。
        return new Queue(DIRECT_QUEUE, true);
    }

    /**
     * 创建一个名为TestDirectExchange的Direct类型的交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        // durable:是否持久化,默认是false,持久化交换机。
        // autoDelete:是否自动删除，交换机先有队列或者其他交换机绑定的时候，然后当该交换机没有队列或其他交换机绑定的时候，会自动删除。
        // arguments：交换机设置的参数，比如设置交换机的备用交换机（Alternate Exchange），当消息不能被路由到该交换机绑定的队列上时，会自动路由到备用交换机
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    /**
     * 绑定交换机和队列
     *
     * @return
     */
    @Bean
    public Binding bindingDirect() {
        //bind队列to交换机中with路由key（routing key）
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }
}

