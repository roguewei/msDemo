package com.rogue.gbf.gbfdemo.controller;

import com.rogue.gbf.gbfdemo.domain.User;
import com.rogue.gbf.gbfdemo.redis.RedisService;
import com.rogue.gbf.gbfdemo.redisutils.UserKey;
import com.rogue.gbf.gbfdemo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weigaosheng
 * @description
 * @CalssName RedisController
 * @date 2019/3/1
 * @params
 * @return
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/get")
    @ResponseBody
    public Result<User> redisGet(){
//        String v1 = redisService.get("key1", String.class);
        User user = redisService.get(UserKey.getById, ""+1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User(1, "1111");
        boolean v1 = redisService.set(UserKey.getByIdOrTimeOut, ""+1, user);
        return Result.success(v1);
    }

}
