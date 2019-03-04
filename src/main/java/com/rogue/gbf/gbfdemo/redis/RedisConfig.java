package com.rogue.gbf.gbfdemo.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author weigaosheng
 * @description
 * @CalssName RedisConfig
 * @date 2019/3/1
 * @params
 * @return
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;
    private String password;
    private int poolMaxIdle;
    private int poolMaxWait;

    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal();
        poolConfig.setMaxIdle(this.poolMaxIdle);
        poolConfig.setMaxWaitMillis(this.poolMaxWait * 1000);
        return new JedisPool(poolConfig, this.host, this.port, this.timeout * 1000, this.password,0);
    }
}
