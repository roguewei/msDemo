package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.domain.Goods;
import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.MiaoshaKey;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaServiceImpl
 * @date 2019/3/7
 * @params
 * @return
 */
@Service
public class MiaoshaServiceImpl implements IMiaoshaService {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisService redisService;

    @Transactional
    @Override
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存
        boolean success = goodsService.reduceStock(goods);
        if(success){
            // 成功之后，下订单(order_info, miaosha_order)
            return orderService.createOrder(user, goods);
        }else{
            // 标记商品已经秒杀完了
            setGoodsOver(goods.getId());
            return null;
        }
    }

    @Override
    public long getMiaoshaResult(Long id, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(id, goodsId);
        if(order != null){
            // 成功
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId){
        // 存在key说明已经秒杀完了
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
