package com.open.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:45
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() {
        String result = stringRedisTemplate.execute(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations operations) throws DataAccessException {
                for (int i = 0; i < 100; i++) {
                    operations.opsForValue().set(String.format("sessionTest:%d", i), "shuai02");
                }
                return (String) operations.opsForValue().get(String.format("sessionTest:%d", 0));
            }
        });
        System.out.println("result:" + result);
    }
}
