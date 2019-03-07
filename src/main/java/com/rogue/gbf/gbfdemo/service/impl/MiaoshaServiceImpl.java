package com.rogue.gbf.gbfdemo.service.impl;

import com.rogue.gbf.gbfdemo.domain.Goods;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
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

    @Transactional
    @Override
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存
        goodsService.reduceStock(goods);

        // 下订单(order_info, miaosha_order)
        return orderService.createOrder(user, goods);
    }
}
