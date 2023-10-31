package com.evaluation.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: RedissonConfig
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/9 16:53
 * {@code @Version}  1.0
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.10.100:6379").setPassword("4869");
        return Redisson.create(config);
    }
}
