package com.huwuwu.learning.web;


import com.huwuwu.learning.commons.response.BaseResponse;
import com.huwuwu.learning.commons.response.ResultUtils;
import com.huwuwu.learning.configuration.rabbitmq.DelayedConfig;
import com.huwuwu.learning.configuration.rabbitmq.DirectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/sendDirectMsg")
    public BaseResponse sendDirectMsg(@RequestBody Map<String,String> map) {
        String message = map.get("message");
        log.info("当前时间：{}，发送一条消息给DIRECT队列：{}", new Date(), message);
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE, DirectConfig.DIRECT_ROUTING_KEY, message, msg -> {
            Map<String, Object> headers = msg.getMessageProperties().getHeaders();
            headers.put("format", "pdf");
            headers.put("type", "report");
            return msg;
        });
        return ResultUtils.success("发送消息给队列");
    }

    @PostMapping("/sendExpirationMsg")
    public BaseResponse sendExpirationMsg(@RequestBody Map<String,String> map) {
        String message = map.get("message");
        String ttlTime = map.get("ttlTime");
        log.info("当前时间：{}，发送一条时长{}毫秒的TTL消息给DIRECT队列：{}", new Date(), ttlTime, message);
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE_B, DirectConfig.DIRECT_ROUTING_KEY_B, message, msg -> {
            //发送消息的时候延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
        return ResultUtils.success("设置过期消息");
    }

    /**
     * 给延迟队列发送消息
     *
     */
    @PostMapping("/sendDelayMsg")
    public BaseResponse sendDelayMsg(@RequestBody Map<String,String> map) {
        String message = map.get("message");
        String delayTime = map.get("delayTime");
        log.info("当前时间：{}，发送一条时长{}毫秒的消息给延迟队列：{}", new Date(), delayTime, message);
        rabbitTemplate.convertAndSend(DelayedConfig.DELAY_EXCHANGE, DelayedConfig.DELAY_ROUTING_KEY, message, msg -> {
            //发送消息的时候延迟时长
            msg.getMessageProperties().setDelay(Integer.valueOf(delayTime));
            return msg;
        });
        return ResultUtils.success("设置延迟消息");
    }


}
