package com.huwuwu.learning.configuration;
 
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * redisson 配置类
 *
 */
@Configuration
public class RedissonConfig {
 
    @Value("${spring.redis.host}")
    private String host;
 
    @Value("${spring.redis.port}")
    private String port;
 
    @Bean
    public RedissonClient redissonClient() {
        // redis 地址为127.0.0.1:6379 时, 可以无需配置 一行代码搞定
//        RedissonClient redisson = Redisson.create();

        //配置类
        Config config = new Config();
        //单机使用
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        //如果是集群的可以使用：config.useClusterServers()
        return Redisson.create(config);

    }
}