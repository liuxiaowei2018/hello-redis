package com.open.redisson;

import com.open.redisson.BloomFilter.BloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
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
@SpringBootTest
public class BloomFilterTest {

    @Autowired
    private BloomFilterService bloomFilterService;

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