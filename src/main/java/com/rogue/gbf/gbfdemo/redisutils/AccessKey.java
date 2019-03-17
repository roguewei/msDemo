package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName MiaoshaKey
 * @date 2019/3/15
 * @params
 * @return
 */
public class AccessKey extends BasePrefix {
    public AccessKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    public AccessKey(String preFix) {
        super(preFix);
    }

    public static AccessKey access = new AccessKey(5, "access");
    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds, "access");
    }
}
