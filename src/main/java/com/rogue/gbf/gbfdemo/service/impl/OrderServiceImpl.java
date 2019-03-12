package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.dao.OrderDao;
import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.OrderKey;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author weigaosheng
 * @description
 * @CalssName OrderServiceImpl
 * @date 2019/3/7
 * @params
 * @return
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getMiaoShaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
//        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    @Override
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        // 下单成功后将秒杀订单信息放到缓存中
        redisService.set(OrderKey.getMiaoShaOrderByUidGid, ""+user.getId()+"_"+goods.getId(), miaoshaOrder);
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
