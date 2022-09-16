package com.open.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:37
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubSubTest {

    public static final String TOPIC = "TEST";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            stringRedisTemplate.convertAndSend(TOPIC, "xiexie:" + i);
            Thread.sleep(1000L);
        }
        //线程编号：listenerContainer-2
        //message：xiexie:0
        //pattern：TEST
        //收到 ChannelTopic 消息：
        //线程编号：listenerContainer-3
        //message：xiexie:1
        //pattern：TEST
        //收到 ChannelTopic 消息：
        //线程编号：listenerContainer-4
        //message：xiexie:2
        //pattern：TEST
    }
}
