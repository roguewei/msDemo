package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.User;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.result.Result;
import com.rogue.gbf.gbfdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weigaosheng
 * @description
 * @CalssName DemoController
 * @date 2019/2/28
 * @params
 * @return
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "hello world";
    }

    @RequestMapping("/suc")
    @ResponseBody
    public Result<String> suc(){
        return Result.success("success hello!");
    }

    @RequestMapping("/err")
    @ResponseBody
    public Result<String> err(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "rogue");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        Boolean b = userService.getTx(1);
        return Result.success(b);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(){
        String v1 = redisService.get("key1", String.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet(){
        boolean v1 = redisService.set("key2", "hello world");
        String v2 = redisService.get("key2", String.class);
        return Result.success(v2);
    }
}
