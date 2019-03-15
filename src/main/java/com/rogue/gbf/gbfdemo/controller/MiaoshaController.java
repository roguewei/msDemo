package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.rabbitmq.MQSender;
import com.rogue.gbf.gbfdemo.rabbitmq.MiaoshaMessage;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.GoodsKey;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.validator.annotation.NeedLogin;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
// 未优化前
//public class MiaoshaController {
// 优化后（项目启动之后发现实现了InitializingBean接口就会回调afterPropertiesSet方法）
public class MiaoshaController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    /**
     * @return a
     * @Author weigaosheng
     * @Description 未优化前的秒杀接口
     * @Date 16:03 2019/3/1
     * @Param
     **/
    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, @NeedLogin MiaoshaUser user, @RequestParam("goodsId") long goodsId){
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
     * 优化前
     * @Date 17:51 2019/3/1
     * @Param
     **/
//    @RequestMapping(value = "/do_miaosha2", method = RequestMethod.POST)
//    @ResponseBody
//    public Result<OrderInfo> do_miaosha2(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
//        if(user == null){
//            return Result.error(CodeMsg.SERVER_ERROR);
//        }
//        // 判断库存
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if(stock <= 0){
//            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }
//        // 判断是否已经秒杀到了
//        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if(miaoshaOrder != null){
//            // 已经秒杀成功了，不能再进行秒杀
//            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//            return Result.error(CodeMsg.REPEATE_MIAOSHA);
//        }
//        // 减库存，下订单，写入秒杀订单
//        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        return Result.success(orderInfo);
//    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 上面方法的改进
     * GET和POST区别
     * get是幂等的，就是说无论调用多少次产生的结果都是一样的，不会对复杂的数据产生影响
     * post想服务端提交数据，对服务端发生变化就用post
     * 优化后
     * @Date 17:51 2019/3/1
     * @Param
     **/
    @RequestMapping(value = "/do_miaosha2", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> do_miaosha2(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        if(user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if(stock < 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(miaoshaOrder != null){
            // 已经秒杀成功了，不能再进行秒杀
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(miaoshaMessage);
        // 返回0代表排队中
        return Result.success(0);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 返回orderid： 成功
     * 返回-1： 库存不足，秒杀失败
     * 返回0： 排队中
     * @Date 16:48 2019/3/1
     * @Param
     **/
    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, long goodsId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 判断用户是否秒杀到商品
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 系统初始化时执行
     * @Date 16:07 2019/3/1
     * @Param
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if(goodsList == null){
            return;
        }
        for(GoodsVo goodsVo : goodsList){
            // 系统启动时加载商品数量到redis中
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
        }
    }
}
