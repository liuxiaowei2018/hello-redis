package com.open.redis.util;

import com.open.redis.util.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 12:39
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilHashTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void keys() {
        System.out.println(redisUtil.keys("set"));
    }

    @Test
    public void putHashValue() {
        for (int i = 0; i < 5; i++) {
            redisUtil.putHashValue("cart:"+i,i+"",new Person(1287989870372388866L, 99));
        }

    }
}