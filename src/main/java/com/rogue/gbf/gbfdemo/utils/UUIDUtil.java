package com.rogue.gbf.gbfdemo.utils;

import java.util.UUID;

/**
 * @author weigaosheng
 * @description
 * @CalssName UUIDUtil
 * @date 2019/3/6
 * @params
 * @return
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
