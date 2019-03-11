package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.GoodsKey;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IGoodsService;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import com.rogue.gbf.gbfdemo.service.impl.MiaoshaUserServiceImpl;
import com.rogue.gbf.gbfdemo.vo.GoodsDetailVo;
import com.rogue.gbf.gbfdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author weigaosheng
 * @description
 * @CalssName GoodsController
 * @date 2019/3/6
 * @params
 * @return
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisService redisService;

    //使用了Thymeleaf，spring就会有ThymeleafViewResolver，用时直接注入就可以
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

//    // @CookieValue(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN) String cookieToken获取客户端请求传来的cookie
//    // @RequestParam(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN) String paramToken这个是兼容客户端用参数形式提交的cookie
//    @RequestMapping("/to_list")
//    public String goodsList(Model model, HttpServletResponse response,
//                            @CookieValue(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN, required = false) String cookieToken,
//                            @RequestParam(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN, required = false) String paramToken){
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user = miaoshaUserService.getByToken(response, token);
//        model.addAttribute("user", user);
//        return "goods_list";
//    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 上面方法的改造，使cookie和参数获取不用在每个接口中都重复写
     * 定义了WebConfig类和UserArgumentResolver类用来获取参数和cookie
     * @Date 16:13 2019/3/
     * @Param
     **/
    // @CookieValue(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN) String cookieToken获取客户端请求传来的cookie
    // @RequestParam(value = MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN) String paramToken这个是兼容客户端用参数形式提交的cookie
    // produces = "text/html"标识返回的是一个html
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
        // spring来渲染页面
//        return "goods_list";

        /* 页面缓存 begin */
        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        // 没有缓存-查数据库
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        model.addAttribute("user", user);

        WebContext springWebContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if(!StringUtils.isEmpty(html)){
            // 保存到缓存中
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
        /* 页面缓存 end */
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String to_detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        // 取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        // 秒杀状态
        int miaoshaStatus = 0;
        // 秒杀剩余时间
        int remainSeconds = 0;
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if(now < startAt){
            // 秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt-now)/1000);
        }else if(now > endAt){
            // 秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{
            // 秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goodsVo);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("user", user);
//        return "goods_detail";

        // 手动渲染
        WebContext springWebContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
        if(!StringUtils.isEmpty(html)){
            // 保存到缓存中
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 页面静态化
     * @Date 14:46 2019/3/1
     * @Param
     **/
    @RequestMapping(value = "/to_detail2/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> to_detail2(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        // 秒杀状态
        int miaoshaStatus = 0;
        // 秒杀剩余时间
        int remainSeconds = 0;
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if(now < startAt){
            // 秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt-now)/1000);
        }else if(now > endAt){
            // 秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{
            // 秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setGoodsVo(goodsVo);
        detailVo.setMiaoshaUser(user);
        detailVo.setMiaoshaStatus(miaoshaStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return Result.success(detailVo);
    }

}
