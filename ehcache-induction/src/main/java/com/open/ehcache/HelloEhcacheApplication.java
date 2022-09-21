package com.open.ehcache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author liuxiaowei
 * @date 2022年09月16日 14:00
 * @Description
 */
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.open.ehcache.mapper")
public class HelloEhcacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloEhcacheApplication.class, args);
    }
}
