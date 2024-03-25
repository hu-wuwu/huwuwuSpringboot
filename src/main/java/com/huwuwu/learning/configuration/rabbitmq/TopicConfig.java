package com.huwuwu.learning.configuration.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    static final String TOPIC_QUEUE_NAME="htTopicQueue";
    static final String TOPIC_QUEUE1_NAME="htTopicQueue1";

    public static final String TOPIC_EXCHANGE_NAME="htTopicExchange";
    /**
     * #匹配0个或多个单词，*匹配一个单词，即使一个的多个routingKey都匹配上了，但该队列只收到一次消息
     */
    public static final String TOPIC="htTopicRouting.*";
    public static final String TOPIC1="htTopicRouting.topic1";


    //发送消息是只需要创建exchange即可
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    //接收消息是需要声明队列，然后通过routingKey将队列与exchange绑定

    //带通配符的topic
    @Bean
    public Queue topicQueue(){
        //durable:true mq服务器重启后让然存在 false重启后队列自动删除
        return new Queue(TOPIC_QUEUE_NAME,true);
    }
    @Bean
    Binding bindingTopic(){
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC);
    }

    //topic1
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1_NAME,false);
    }
    @Bean
    Binding bindingTopic1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC1);
    }
}
