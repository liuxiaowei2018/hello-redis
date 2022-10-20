package com.open.redis.util;

import com.open.redis.util.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 12:39
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilZsetTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void keys() {
        System.out.println(redisUtil.keys("set"));
    }

    @Test
    public void addZSet() {
        // 初始化person集合
        List<Person> personList = Arrays.asList(new Person(1287989870372388866L, 29), new Person(1287989874193399810L, 18), new Person(1287989878043770883L, 38), new Person(1287989866203250690L, 12), new Person(1287989956804411394L, 45));
        for (int i = 0; i < personList.size(); i++) {
            // 新增，key为person，value为uuid唯一标识，score为年龄
            System.out.println(redisUtil.addZSet("person", personList.get(i).getUuid(), personList.get(i).getAge()));
        }
    }

    @Test
    public void countZSet() {
        // 查询key为person的集合个数
        Long count = redisUtil.countZSet("person");
        System.out.println(count);

    }

    @Test
    public void rangeZSet() {
        // 查询所有
        System.out.println(redisUtil.rangeZSet("person", 0, -1));
        // 结果 [1287989866203250690, 1287989874193399810, 1287989870372388866, 1287989878043770883, 1287989956804411394]

        // 查询前3个
        System.out.println(redisUtil.rangeZSet("person", 0, 2));
        // 结果 [1287989866203250690, 1287989874193399810, 1287989870372388866]
    }

    //  removeZSet
    @Test
    public void removeZSet() {

        // 删除指定key和value对应的元素（存在的）
        System.out.println(redisUtil.removeZSet("person", 1287989874193399810L));
        // 结果 1

        // 删除指定key和value对应的元素（不存在的）
        System.out.println(redisUtil.removeZSet("111", 1287989874193399810L));
        // 结果 0

        // 查询删除后的所有元素
        System.out.println(redisUtil.rangeZSet("person", 0, -1));

    }

}