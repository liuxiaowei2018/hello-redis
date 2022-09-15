package com.open.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:39
 * @Description
 */
public class TestChannelTopicMessageListener implements MessageListener {

    /**
     * 默认的 RedisMessageListenerContainer 情况下，MessageListener 是并发消费
     * @date 2022/9/14 16:40
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("收到 ChannelTopic 消息：");
        System.out.println("线程编号：" + Thread.currentThread().getName());
        System.out.println("message：" + message);
        System.out.println("pattern：" + new String(pattern));
    }
}
