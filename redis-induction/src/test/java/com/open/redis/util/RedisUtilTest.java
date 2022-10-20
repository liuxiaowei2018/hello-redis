package com.open.redis.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 12:39
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void keys() {
        System.out.println(redisUtil.keys("set"));
    }

    @Test
    public void exists() {
        System.out.println(redisUtil.exists("set-two"));
        System.out.println(redisUtil.exists("set-two222"));
    }
}