package com.rogue.gbf.gbfdemo.redisutils;

/**
 * @Author weigaosheng
 * @Description 同样缓存key封装
 * @Date 10:54 2019/3/4
 * @Param
 * @return
 **/
public interface KeyPrefix {
    // 过期时间
    public int expireSeconds();
    // 前缀
    public String getPrefix();
}
