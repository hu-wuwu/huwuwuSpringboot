package com.huwuwu.learning.web;


import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.configuration.rabbitmq.DelayedRabbitConfig;
import com.huwuwu.learning.configuration.rabbitmq.DirectRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * RabbitMQ
 */
@Slf4j
@RequestMapping("/publisher")
@RestController
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public BaseResponse sendMsg(@PathVariable String message) {
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}", new Date(), message);
        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_ROUTING_KEY, message, msg -> {
            Map<String, Object> headers = msg.getMessageProperties().getHeaders();
            headers.put("format", "pdf");
            headers.put("type", "report");
            return msg;
        });
        return ResultUtils.success("发送消息给队列");
    }

    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{}，发送一条时长{}毫秒的TTL消息给normal03队列：{}", new Date(), ttlTime, message);
        rabbitTemplate.convertAndSend("normal_exchange", "normal03", message, msg -> {
            //发送消息的时候延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    /**
     * 给延迟队列发送消息
     *
     * @param message
     * @param delayTime
     */
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        log.info("当前时间：{}，发送一条时长{}毫秒的消息给延迟队列：{}", new Date(), delayTime, message);
        rabbitTemplate.convertAndSend(DelayedRabbitConfig.DELAY_EXCHANGE, DelayedRabbitConfig.DELAY_ROUTING_KEY, message, msg -> {
            //发送消息的时候延迟时长
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }


}
