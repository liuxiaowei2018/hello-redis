package com.open.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.Collections;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:48
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() throws IOException {
        // <1.1> 读取 /resources/lua/compareAndSet.lua 脚本 注意，需要引入下 commons-io 依赖。
        String  scriptContents = IOUtils.toString(getClass().getResourceAsStream("/lua/compareAndSet.lua"), "UTF-8");
        // <1.2> 创建 RedisScript 对象
        RedisScript<Long> script = new DefaultRedisScript<>(scriptContents, Long.class);
        // <2> 执行 LUA 脚本
        Long result = stringRedisTemplate.execute(script, Collections.singletonList("luna:1"), "shuai02", "shuai");
        System.out.println(result);
    }
}
