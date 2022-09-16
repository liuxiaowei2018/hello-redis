package com.open.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年09月16日 13:42
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RateLimiterTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() throws InterruptedException {
        // 创建 RRateLimiter 对象
        RRateLimiter rateLimiter  = redissonClient.getRateLimiter("myRateLimiter");
        // 初始化：最大流速 = 每 1 秒钟产生 2 个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%s：获得锁结果(%s)", simpleDateFormat.format(new Date()), rateLimiter.tryAcquire()));
            Thread.sleep(250L);
        }
        //2022-09-16 13:46:19：获得锁结果(true)
        //2022-09-16 13:46:19：获得锁结果(true)
        //2022-09-16 13:46:20：获得锁结果(false)
        //2022-09-16 13:46:20：获得锁结果(false)
        //2022-09-16 13:46:20：获得锁结果(true)
        //第 1、2 次，成功获取锁。
        //第 3、4 次，因为每 1 秒产生 2 个令牌，所以被限流了。
        //第 5 次，已经过了 1 秒，所以获得令牌成功。
    }
}
