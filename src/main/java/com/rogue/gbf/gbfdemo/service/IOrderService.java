package com.rogue.gbf.gbfdemo.service;

import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;

public interface IOrderService {
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long id, long goodsId);

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
}
