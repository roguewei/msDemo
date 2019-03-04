package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @author weigaosheng
 * @description
 * @CalssName BasePrefix
 * @date 2019/3/4
 * @params
 * @return
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String preFix;

    public BasePrefix(int expireSeconds, String preFix){
        this.expireSeconds = expireSeconds;
        this.preFix = preFix;
    }

    public BasePrefix(String preFix) {
        // 0代表永不过期
        this(0, preFix);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 默认0永不过期
     * @Date 10:58 2019/3/4     * @Param
     **/
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":"+preFix;
    }
}
