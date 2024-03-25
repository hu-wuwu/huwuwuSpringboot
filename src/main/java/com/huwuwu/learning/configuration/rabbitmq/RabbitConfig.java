package com.huwuwu.learning.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //设置消息投递失败的策略，有两种策略：自动删除或返回到客户端。
        //mandatory必须得设置成true，否则无法回调ReturnsCallback。
        //我们既然要做可靠性，当然是设置为返回到客户端(true是返回客户端，false是自动删除)
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("ConfirmCallback 关联数据：{},投递成功,确认情况：{}", correlationData, ack);
                } else {
                    log.info("ConfirmCallback 关联数据：{},投递失败,确认情况：{}，原因：{}", correlationData, ack, cause);
                }
            }
        });


        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                // 如果配置了发送回调ReturnCallback，插件延迟队列则会回调该方法，
                // 因为发送方确实没有投递到队列上，只是在交换器上暂存，等过期时间到了 才会发往队列。
                // 并非是BUG，而是有原因的，建议利用if 去拦截这个异常，判断延迟队列交换机名称，然后break;
                if (returnedMessage.getExchange().equals(DelayedConfig.DELAY_EXCHANGE)) {
                    return;
                }
                    log.info("ReturnsCallback 消息：{},回应码：{},回应信息：{},交换机：{},路由键：{}"
                        , returnedMessage.getMessage(), returnedMessage.getReplyCode()
                        , returnedMessage.getReplyText(), returnedMessage.getExchange()
                        , returnedMessage.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }
}
