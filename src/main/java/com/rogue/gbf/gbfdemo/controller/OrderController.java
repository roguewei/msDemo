package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.validator.annotation.NeedLogin;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import com.rogue.gbf.gbfdemo.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weigaosheng
 * @description
 * @CalssName OrderController
 * @date 2019/3/12
 * @params
 * @return
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result<OrderDetailVo> detail(Model model, @NeedLogin MiaoshaUser user, @RequestParam("orderId") long orderId){

        // 该判断用@NeedLogin注解去判断
//        if(user == null){
//            return Result.error(CodeMsg.SESSION_ERROR);
//        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if(orderInfo == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrderInfo(orderInfo);
        vo.setGoodsVo(goods);

        return Result.success(vo);

    }

}
