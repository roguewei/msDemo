package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;
import com.rogue.gbf.gbfdemo.service.IMiaoshaUserService;
import com.rogue.gbf.gbfdemo.service.impl.MiaoshaUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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
    private IMiaoshaUserService miaoshaUserService;

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
    @RequestMapping("/to_list")
    public String goodsList(Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        return "goods_list";
    }

}
