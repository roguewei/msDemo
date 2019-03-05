package com.rogue.gbf.gbfdemo.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weigaosheng
 * @description
 * @CalssName ValidatorUtil
 * @date 2019/3/5
 * @params
 * @return
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }else{
            Matcher matcher = mobile_pattern.matcher(src);
            return matcher.matches();
        }
    }

//    public static void main(String[] args) {
//        System.out.println(isMobile("15622222222"));
//        System.out.println(isMobile("1562222222"));
//    }

}
