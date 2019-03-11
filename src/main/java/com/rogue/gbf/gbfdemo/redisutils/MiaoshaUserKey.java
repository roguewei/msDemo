package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaUserKey
 * @date 2019/3/6
 * @params
 * @return
 */
public class MiaoshaUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24*2;

    private MiaoshaUserKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private MiaoshaUserKey(String preFix){
        super(preFix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");

    public static MiaoshaUserKey getById = new MiaoshaUserKey( "id");

}
