package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaUserKey
 * @date 2019/3/6
 * @params
 * @return
 */
public class GoodsKey extends BasePrefix {


    private GoodsKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private GoodsKey(String preFix){
        super(preFix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

}
