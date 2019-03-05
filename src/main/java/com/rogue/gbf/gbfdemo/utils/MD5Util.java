package com.rogue.gbf.gbfdemo.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author weigaosheng
 * @description
 * @CalssName MD5Util
 * @date 2019/3/4
 * @params
 * @return
 */
public class MD5Util {

    // 加密盐
    private static final String salt = "1sdfw5we8dfhtr6sd";

    /**
     * @return a
     * @Author weigaosheng
     * @Description 一次MD5加密
     * @Date 17:27 2019/3/4
    * @Param
     **/
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 加上盐的自定义截取字符再加用户数据进行MD5加密
     * @Date 17:29 2019/3/4
    * @Param
     **/
    public static String inputPassToFormPass(String inputPass){
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5)+ salt.charAt(4);
        return md5(str);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 二次MD5加密
     * @Date 17:33 2019/3/
     * @Param
     **/
    public static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5)+ salt.charAt(4);
        return md5(str);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 把用户输入的明文密码转换成数据库保存的密码
     * @Date 17:29 2019/3/4
     * @Param
     **/
    public static String inputPassToDbPass(String input, String saltDB){
        // 1、将明文密码转换成form密码
        String formPass = inputPassToFormPass(input);
        // 2、把form密码转换成db里面的密码
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d5f6e"));
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }

}
