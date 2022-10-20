package com.open.redis.config;

import com.open.redis.listener.TestChannelTopicMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:00
 * @Description
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 RedisConnection 工厂
        template.setConnectionFactory(factory);

        // 使用 String 序列化方式，序列化 KEY 。
        template.setKeySerializer(RedisSerializer.string());
        // 使用 JSON 序列化方式（库是 Jackson），序列化 VALUE -> GenericJackson2JsonRedisSerializer
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());

        return template;
    }

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory) {
        // 创建 RedisMessageListenerContainer 对象
        RedisMessageListenerContainer container  = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // RedisConnectionFactory 可以多次调用 #addMessageListener(MessageListener listener, Topic topic) 方法，但是一定要都是相同的 Topic 类型。
        // 例如说，添加了 ChannelTopic 类型，就不能添加 PatternTopic 类型。为什么呢？
        // 因为 RedisMessageListenerContainer 是基于一次 SUBSCRIBE 或 PSUBSCRIBE 命令，所以不支持不同类型的 Topic 。
        // 当然，如果是相同类型的 Topic ，多个 MessageListener 是支持的。
        container.addMessageListener(new TestChannelTopicMessageListener(),new ChannelTopic("TEST"));
        //container.addMessageListener(new TestChannelTopicMessageListener(), new ChannelTopic("AOTEMAN"));
        //container.addMessageListener(new TestPatternTopicMessageListener(), new PatternTopic("TEST"));
        return container;
    }

}
