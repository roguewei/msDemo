package com.rogue.gbf.gbfdemo.rabbitmq;

import com.rogue.gbf.gbfdemo.config.MQConfig;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weigaosheng
 * @description
 * @CalssName MQSender
 * @date 2019/3/14
 * @params
 * @return
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object message){
        String msg = RedisService.beanToString(message);
        log.info(msg+"--------send");
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info(msg+"--------send topic message");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY1, msg+"---1---");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY2, msg+"---2---");
    }

    public void sendFanout(Object message){
        String msg = RedisService.beanToString(message);
        log.info(msg+"--------send fanout message");
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg+"---1---");
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg+"---2---");
    }

    public void sendHeader(Object message){
        String msg = RedisService.beanToString(message);
        log.info(msg+"--------send fanout message");
        MessageProperties properties = new MessageProperties();
        // 此处的key/value需要跟MQConfig里面的配置的一样
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
    }

    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String msg = RedisService.beanToString(miaoshaMessage);
        log.info("send message "+ msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }
}
