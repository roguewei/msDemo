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
import com.rogue.gbf.gbfdemo.utils.MD5Util;
import com.rogue.gbf.gbfdemo.utils.UUIDUtil;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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

    @Override
    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if(user == null || path == null){
            return false;
        }
        String savePath = redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, String.class);
        return path.equals(savePath);
    }

    @Override
    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        if(user == null || goodsId <= 0){
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid())+"123456";
        redisService.set(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, str);
        return str;
    }

    @Override
    public BufferedImage createVerifyCode(MiaoshaUser user, long goodsId) {
        if(user == null || goodsId <= 0){
            return null;
        }
        int width = 80;
        int height = 32;
        // create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0,0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0,0, width-1, height-1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make som confusion
        // 生成50个干扰的点
        for(int i=0; i<50; i++){
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x,y,0,0);
        }
        // generate a random code 将验证码生成出来并画出来
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        // 把验证码存到Redis
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaPathVerifyCode, user.getId()+","+goodsId, rnd);
        return image;
    }

    @Override
    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        if(user == null || goodsId <= 0){
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaPathVerifyCode, user.getId()+","+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0){
            return false;
        }
        // 验证通过删除Redis中的数据
        redisService.del(MiaoshaKey.getMiaoshaPathVerifyCode, user.getId()+","+goodsId);
        return true;
    }

    private static char[] ops = new char[]{'+', '-', '*'};


     /*
     * @Author Jimmy
     * @Description 只做+ - *计算
     * @Date 23:52 2019/3/16
     * @Param
     * @return
     **/
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        // 表达式
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    private int calc(String exp) {
        try{
            // 表达式引擎
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (int) engine.eval(exp);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
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
