package com.open.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:34
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    //@Transactional
    public void test01() {
        stringRedisTemplate.setEnableTransactionSupport(true);
        // 执行想要的操作
        stringRedisTemplate.opsForValue().set("xx:1", "shuai");
        stringRedisTemplate.opsForValue().set("xxx:1", "dai");
    }
}
