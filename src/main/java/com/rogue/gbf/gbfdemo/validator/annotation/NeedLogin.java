package com.rogue.gbf.gbfdemo.validator.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author weigaosheng
 * @Description 用注解方式来判断是否登录
 * @Date 16:29 2019/3/12
 * @Param
 * @return
 **/
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {

    String message() default "登录超时，请重新登录！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
