package com.huwuwu.learning.listener.rabbitListener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 延迟队列消费者
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    @RabbitHandler//根据不同的消息类型进行不同的处理
//    @RabbitListener(queues = DelayedConfig.DELAY_QUEUE)
    private void receiveDelayQueue(Message message, Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
        String msg = new String(message.getBody());
        log.info("当前时间{}，收到延迟队列的消息",new Date(),msg);
        channel.basicAck(deliveryTag, false);
    }

}