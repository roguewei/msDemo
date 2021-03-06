package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaKey
 * @date 2019/3/15
 * @params
 * @return
 */
public class MiaoshaKey extends BasePrefix {
    public MiaoshaKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    public MiaoshaKey(String preFix) {
        super(preFix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(30, "path");
    public static MiaoshaKey getMiaoshaPathVerifyCode = new MiaoshaKey(300, "vc");
}
