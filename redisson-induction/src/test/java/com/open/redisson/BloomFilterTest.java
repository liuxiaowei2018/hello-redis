package com.open.redisson;

import com.open.HelloApplication;
import com.open.redisson.BloomFilter.BloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 20:22
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloApplication.class)
public class BloomFilterTest {

    @Autowired
    private BloomFilterService bloomFilterService;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testBloomFilterSimple() {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("person");
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add("Tom");
        bloomFilter.add("Jack");
        System.out.println(bloomFilter.count());   //2
        System.out.println(bloomFilter.contains("Tom"));  //true
        System.out.println(bloomFilter.contains("Linda"));  //false
    }

    @Test
    public void testBloomFilter() {

        // 预期插入数量
        long expectedInsertions = 10000L;
        // 错误比率
        double falseProbability = 0.01;
        RBloomFilter<Long> bloomFilter = bloomFilterService.create("ipBlackList", expectedInsertions, falseProbability);

        // 布隆过滤器增加元素
        for (long i = 0; i < expectedInsertions; i++) {
            bloomFilter.add(i);
        }
        long elementCount = bloomFilter.count();
        log.info("elementCount = {}.", elementCount);

        // 统计误判次数
        int count = 0;
        for (long i = expectedInsertions; i < expectedInsertions * 2; i++) {
            if (bloomFilter.contains(i)) {
                count++;
            }
        }
        log.info("误判次数 = {}.", count);
        bloomFilter.delete();
    }

}