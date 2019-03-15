package com.rogue.gbf.gbfdemo.rabbitmq;

import com.rogue.gbf.gbfdemo.config.MQConfig;
import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weigaosheng
 * @description
 * @CalssName MQReceiver
 * @date 2019/3/14
 * @params
 * @return
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info(message+"------------receive");
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_1)
    public void receiveTopic1(String message){
        log.info(message+"------------receive topic queue1");
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE_2)
    public void receiveTopic2(String message){
        log.info(message+"------------receive topic queue2");
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receiveHeader(byte[] message){
        log.info(new String(message)+"------------receiveHeader queue2");
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMS(String message){
        log.info(message+"------------receive");
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if(stock <= 0){
            return;
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){
            // 如果秒杀到了则不做处理
            return;
        }
        // 真正执行减库存，下订单，写入秒杀订单
        miaoshaService.miaosha(user, goods);

    }
}
