package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName OrderKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class OrderKey extends BasePrefix {

    private OrderKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private OrderKey(String preFix) {
        super(preFix);
    }

    public static OrderKey getById = new OrderKey("id");
    public static OrderKey getByIdOrTimeOut = new OrderKey(60*60,"id");

    public static OrderKey getByNameOrTimeOut = new OrderKey("name");
    public static OrderKey getByName = new OrderKey(60*60,"name");
}
