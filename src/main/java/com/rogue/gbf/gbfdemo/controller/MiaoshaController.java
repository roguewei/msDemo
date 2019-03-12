package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weigaoshneg
 * @description
 * @CalssName MiaoshaController
 * @date 2019/3/7
 * @params
 * @return
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        if(user == null){
            return "login";
        }
        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            // 已经秒杀成功了，不能再进行秒杀
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 上面方法的改进
     * GET和POST区别
     * get是幂等的，就是说无论调用多少次产生的结果都是一样的，不会对复杂的数据产生影响
     * post想服务端提交数据，对服务端发生变化就用post
     * @Date 17:51 2019/3/1
     * @Param
     **/
    @RequestMapping(value = "/do_miaosha2", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> do_miaosha2(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        if(user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            // 已经秒杀成功了，不能再进行秒杀
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }

}
