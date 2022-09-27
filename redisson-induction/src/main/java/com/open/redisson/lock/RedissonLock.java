package com.open.redisson.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: liuxiaowei
 * @date: 2021年05月23日 21:20
 */
@Component
@Slf4j
public class RedissonLock {

    @Resource
    private RedissonClient redissonClient;

    // 如果传递了锁的超时时间，就执行脚本，进行占锁;
    // 如果没传递锁时间，使用看门狗的时间，占锁。如果返回占锁成功future，调用future.onComplete();
    // 没异常的话调用 scheduleExpirationRenewal(threadId);
    // 重新设置过期时间，定时任务;
    // 看门狗的原理是定时任务：重新给锁设置过期时间，新的过期时间就是看门狗的默认时间;
    // 锁时间/3 是定时任务周期;

    /**
     * Redisson实现分布式锁
     *
     * @return
     */
    public Map<String, String> getDataWithRedissonLock() {
        Map<String, String> data = null;
        // 只要锁的名字一样那锁就是一样的
        // 关于锁的粒度=具体缓存的是某个数据->例如: 11-号商品 product-11-lock

        //1.指定时间-自动解锁，看门狗不续命
        //2.不指定时间-看门狗的检查锁的超时时间是30秒钟（每到20s就会自动续借成30s）
        RLock lock = redissonClient.getLock("Redisson-Lock");
        lock.lock();//阻塞等待
        try {
            Thread.sleep(30000);
            data = getDataFromDB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 看门狗，不用担心这里宕机
            lock.unlock();
            return data;
        }
    }

    /**
     * 模拟数据库返回
     *
     * @return
     */
    private Map<String, String> getDataFromDB() {
        HashMap<String, String> map = new HashMap<>();
        map.put("测试数据", "测试");
        return map;
    }
}
