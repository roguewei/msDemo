package com.rogue.gbf.gbfdemo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author weigaosheng
 * @description
 * @CalssName RedisService
 * @date 2019/3/1
 * @params
 * @return
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz){
        Jedis jedis = jedisPool.getResource();
    }

}
