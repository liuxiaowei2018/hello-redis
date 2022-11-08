package com.open.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @author: liuxiaowei
 * @date: 2021年05月23日 21:17
 */
@Configuration
public class RedissonConfig {

    /**
     * redission通过redissonClient对象使用
     * 如果是多个redis集群，可以配置
     *
     * @return org.redisson.api.RedissonClient
     */
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson() {
//        Config config = new Config();
//        // 创建单例模式的配置
//        config.useSingleServer().setAddress("redis://" + "ip" + ":16379");
//        return Redisson.create(config);
//    }
}
