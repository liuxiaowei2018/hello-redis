package com.open.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:31
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PipelineTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() {
        List<Object> results  = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            // set 写入
            for (int i = 0; i < 3; i++) {
                connection.set(String.format("demo:%d", i).getBytes(), "gogogo".getBytes());
            }
            // get
            for (int i = 0; i < 3; i++) {
                connection.get(String.format("demo:%d", i).getBytes());
            }
            return null;
        });
        // 打印结果
        System.out.println(results);
    }
}
