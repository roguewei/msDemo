package com.rogue.gbf.gbfdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weigaosheng
 * @description
 * @CalssName MQConfig
 * @date 2019/3/14
 * @params
 * @return
 */
@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    public static final String QUEUE = "queue";

    public static final String HEADER_QUEUE = "header.queue";

    public static final String TOPIC_QUEUE_1 = "topic.queue1";
    public static final String TOPIC_QUEUE_2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicexchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";

    public static final String FANOUT_EXCHANGE = "fanoutexchange";

    public static final String HEADERS_EXCHANGE = "headersexchange";

    @Bean
    public Queue miaoshaqueue(){
        return new Queue(MIAOSHA_QUEUE, true);
    }

    /* Direct模式下的交换机Exchange模式 begin */
    /**
     * @return a
     * @Author weigaosheng
     * @Description Direct模式下的交换机Exchange模式
     * @Date 17:59 2019/3/1
     * @Param
     **/
    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true);
    }
    /* Direct模式下的交换机Exchange模式 end */

    /* Topic模式下的交换机Exchange模式 begin */
    /**
     * @return a
     * @Author weigaosheng
     * @Description Topic模式下的交换机Exchange模式
     * @Date 17:59 2019/3/1
     * @Param
     **/
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE_1, true);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description Topic模式下的交换机Exchange模式
     * @Date 17:59 2019/3/1
     * @Param
     **/
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE_2, true);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 把消息放到exchange中，然后把exchange放到topic中
     * @Date 8:47 2019/3/1
     * @Param
     **/
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 使用ROUTING_KEY1作为key时绑定到topicQueue1()
     * @Date 8:51 2019/3/1
     * @Param
     **/
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 使用ROUTING_KEY2作为key时绑定到topicQueue2()
     * @Date 8:52 2019/3/1
     * @Param
     **/
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }
    /* Topic模式下的交换机Exchange模式 end */

    /* Fanout模式下的交换机Exchange模式 begin */
    /**
     * @return a
     * @Author weigaosheng
     * @Description Fanout模式 交换机Exchange
     * @Date 8:52 2019/3/1
     * @Param
     **/
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }
    /* Fanout模式下的交换机Exchange模式 end */

    /* Header模式下的交换机Exchange模式 begin */
    /**
     * @return a
     * @Author weigaosheng
     * @Description Header模式 交换机Exchange
     * @Date 8:52 2019/3/1
     * @Param
     **/
    @Bean
    public HeadersExchange headerExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headerQueue1(){
        return new Queue(HEADER_QUEUE, true);
    }

    @Bean
    public Binding headerBinding(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headerQueue1()).to(headerExchange()).whereAll(map).match();
    }

}
