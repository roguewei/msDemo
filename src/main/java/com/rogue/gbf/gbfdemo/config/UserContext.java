package com.rogue.gbf.gbfdemo.config;

import com.rogue.gbf.gbfdemo.domain.MiaoshaUser;

/**
 * @ClassName UserContext
 * @Description
 * @Author Jimmy
 * @Date 2019/3/17 11:33
 * @Version 1.0
 **/
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(){
        return userHolder.get();
    }

}
