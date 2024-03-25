package com.huwuwu.learning.configuration.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HeadersConfig {

    static final String HEADERS_QUEUE_NAME="htHeadersQueue";

    public static final String HEADERS_EXCHANGE_NAME="htHeadersExchange";

    //发送消息是只需要创建exchange即可
    @Bean
    HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
    }

    @Bean
    public Queue headersQueue(){
        return new Queue(HEADERS_QUEUE_NAME,false);
    }

    @Bean
    Binding bindingHeaders(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("format",  "pdf");
        arguments.put("type",  "report");
        //x-match为any
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAny(arguments).match();
    }

    @Bean
    Binding bindingAllHeaders(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("format",  "pdf");
        arguments.put("type",  "report");
        //x-match为all
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(arguments).match();
    }
}
