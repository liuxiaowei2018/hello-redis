package com.open.redis;

import cn.hutool.core.convert.Convert;
import com.open.redis.util.TaylorUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:01
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testId() {
        Set objects = new HashSet<>();
        long sum = 0L;
        for (int i = 0; i < 1000; i++) {
            List<Long> id2022 = getResult("id20221103", 1000);
            for (Long s : id2022) {
                log.info(String.valueOf(s));
                sum++;
                if (objects.contains(id2022)) {
                   log.error("重复---{}",s);
                }
                objects.add(s);
            }
        }
        log.info("操作次数---:{}",sum);
    }

    private List<Long> getResult(String key, Integer num) {
        Object obj = redisTemplate.opsForValue().get(key);
        Long memoryIncr;
        if (obj == null) {
            memoryIncr = 10000000000L;
        } else {
            if (obj instanceof CharSequence) {
                memoryIncr = Long.parseLong(((String) obj));
            } else {
                memoryIncr = Convert.toLong(obj);
            }
        }
        Long increment = redisTemplate.opsForValue().increment(key, num == null ? 1 : num);
        ArrayList<Long> codes = new ArrayList<>();
        for (; memoryIncr < increment; ) {
            codes.add(TaylorUtil.next(memoryIncr));
            memoryIncr++;
        }
        return codes;
    }

    public static void main(String[] args){
        Set set = new HashSet();
        for(int i=0;; i++) {
            long id = TaylorUtil.next(i);
            System.out.println(id +"---"+i);
            if(set.contains(id)){
                System.out.println("duplicated");
                break;
            }
            set.add(id);
        }
    }



}