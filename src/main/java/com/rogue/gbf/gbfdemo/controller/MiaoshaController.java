package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaOrder;
import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.domain.OrderInfo;
import com.rogue.gbf.gbfdemo.rabbitmq.MQSender;
import com.rogue.gbf.gbfdemo.rabbitmq.MiaoshaMessage;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.AccessKey;
import com.rogue.gbf.gbfdemo.redisutils.GoodsKey;
import com.rogue.gbf.gbfdemo.redisutils.MiaoshaKey;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaService;
import com.rogue.gbf.gbfdemo.service.IOrderService;
import com.rogue.gbf.gbfdemo.utils.MD5Util;
import com.rogue.gbf.gbfdemo.utils.UUIDUtil;
import com.rogue.gbf.gbfdemo.validator.annotation.AccessLimit;
import com.rogue.gbf.gbfdemo.validator.annotation.NeedLogin;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

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
    @AccessLimit(seconds = 60, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/{path}/do_miaosha2", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> do_miaosha2(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId, @PathVariable("path")String path){
        if(user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if(!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        // 做一个标记，减少Redis访问开销
        boolean over = localOverMap.get(goodsId);
        if(over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if(stock < 0){
            // 当没有库存时将标记更改，下一个用户请求时如果没有库存了则不再访问Redis了，减少了Redis的开销
            localOverMap.put(goodsId, true);
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
    public Result<Long> miaoshaResult(MiaoshaUser user, long goodsId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 判断用户是否秒杀到商品
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /*
     * @Author Jimmy
     * @Description 隐藏秒杀地址接口
     * @Date 23:29 2019/3/16
     * @Param
     * @return
     **/
    @AccessLimit(seconds = 60, maxCount = 5, needLogin = true)
    @RequestMapping(value = "path")
    @ResponseBody
    public Result<String> getPath(MiaoshaUser user, long goodsId,@RequestParam(value = "verifyCode") int verifyCode){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 验证码是否正确
        boolean check = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if(!check){
            return Result.error(CodeMsg.VERIFY_ERROR);
        }
        String path = miaoshaService.createMiaoshaPath(user, goodsId);
        return Result.success(path);
    }


    /*
     * @Author Jimmy
     * @Description 数学公式图片验证码
     * @Date 23:29 2019/3/16
     * @Param
     * @return
     **/
    @RequestMapping(value = "verifyCode")
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(HttpServletResponse response, MiaoshaUser user, long goodsId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        BufferedImage image = miaoshaService.createVerifyCode(user, goodsId);
        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
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
            // 初始化时做一个标记，表明还有库存
            localOverMap.put(goodsVo.getId(), false);
        }
    }
}
