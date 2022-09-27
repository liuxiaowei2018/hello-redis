package com.open.redis.lock;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @author: liuxiaowei
 * @date: 2021年05月23日 21:03
 */
@Component
@Slf4j
public class LuaRedisLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Lua脚本实现分布式锁
     * @return
     */
    public Map<String, String> getDataWithRedisLock() {
        // 1.占分布式锁  设置这个锁30秒自动删除 [原子操作]
        String uuid = RandomUtil.randomNumbers(11);
        Boolean lock = stringRedisTemplate.opsForValue()
                .setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        if (lock) {
            // 2.设置过期时间加锁成功 获取数据释放锁
            // [分布式下必须是Lua脚本删锁,不然会因为业务处理时间、网络延迟等等引起数据还没返回锁过期或者返回的过程中过期-把别人的锁删了]
            Map<String, String> data;
            try {
                data = getDataFromDB();
            } finally {
                String lockValue = stringRedisTemplate.opsForValue().get("lock");
                // 删除也必须是原子操作 Lua脚本操作 删除成功返回1 否则返回0
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(
                        // 脚本和返回类型
                        new DefaultRedisScript<>(script, Long.class),
                        // 参数
                        Arrays.asList("lock"),
                        // 参数值，锁的值
                        uuid);
            }
            return data;
        } else {
            // 重试加锁
            try {
                // 延迟50毫秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getDataWithRedisLock();
        }
    }

    /**
     * 模拟数据库返回
     * @return
     */
    private Map<String, String> getDataFromDB() {
        HashMap<String, String> map = new HashMap<>(16);
        map.put("测试数据", "测试");
        return map;
    }
}
