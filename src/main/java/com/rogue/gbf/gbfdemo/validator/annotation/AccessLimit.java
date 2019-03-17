package com.rogue.gbf.gbfdemo.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
 * @Author Jimmy
 * @Description 接口限流的注解
 * @Date 11:16 2019/3/17
 * @Param
 * @return
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    // 多少秒之内
    int seconds();
    // 访问次数
    int maxCount();
    // 是否需要登录
    boolean needLogin() default true;

}
