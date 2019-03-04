package com.rogue.gbf.gbfdemo.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str, clazz);
            return t;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0){
                return false;
            }
            jedis.set(key, str);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 把json对象转换成字符串
     * @Date 9:58 2019/3/
     * @Param
     **/
    private <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return "" + value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+ value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 把string类型的数据转换成对象
     * @Date 10:25 2019/3/
     * @Param
     **/
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String string, Class<T> clazz) {
        if(StringUtils.isEmpty(string) || clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(string);
        }else if(clazz == String.class){
            return (T) string;
        }else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(string);
        }else{
            return JSON.toJavaObject(JSON.parseObject(string), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis == null){
            jedis.close();
        }
    }

}
