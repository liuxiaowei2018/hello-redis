package com.open.redis.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 12:39
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilSetTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void addSet() {
        System.out.println(redisUtil.addSet("word-book", "article", "boy", "cat", "dog", "English"));
        System.out.println(redisUtil.addSet("chinese-word", "年", "月", "日"));
    }

    @Test
    public void removeSet() {
        // key和value能查询到，返回1；
        System.out.println(redisUtil.removeSet("word-book", "cat"));
        // key不存在 或 存在对应的key，value不存在，返回0
        System.out.println(redisUtil.removeSet("111", "cat"));

        System.out.println(redisUtil.removeSet("chinese-word", "年"));
    }

    @Test
    public void countSet() {
        // key存在，返回个数
        System.out.println(redisUtil.countSet("word-book"));
        // key不存在，0
        System.out.println(redisUtil.countSet("111"));
    }

    @Test
    public void hasMemberSet() {
        // key和value存在，返回true
        System.out.println(redisUtil.hasMemberSet("word-book", "boy"));
        // key存在，value不存在，返回false
        System.out.println(redisUtil.hasMemberSet("word-book", "111"));
        // key不存在，返回false
        System.out.println(redisUtil.hasMemberSet("111", "111"));
    }

    @Test
    public void randomMember() {
        // key存在，返回元素
        System.out.println(redisUtil.randomMember("word-book"));
        // key不存在，返回null
        System.out.println(redisUtil.randomMember("111"));
    }

    @Test
    public void randomNumMember() {
        // key存在，随机返回5个，可能重复
        System.out.println(redisUtil.randomMembers("word-book", 5));
    }

    @Test
    public void distinctRandomNumMember() {
        // key存在，返回不重复的5个元素
        System.out.println(redisUtil.distinctRandomMembers("word-book", 5));
    }

    @Test
    public void members() {
        System.out.println(redisUtil.members("chinese-word"));
    }

    @Test
    public void moveMember() {
        System.out.println(redisUtil.moveMember("word-book", "article", "chinese-word"));
        System.out.println(redisUtil.members("word-book"));
        System.out.println(redisUtil.members("chinese-word"));
    }

    @Test
    public void intersectMember() {
        System.out.println(redisUtil.addSet("set-one", "one", "two", "three", "four", "five"));
        System.out.println(redisUtil.addSet("set-two", "one", "three", "ten", "nine"));
        System.out.println(redisUtil.intersect("set-one", "set-two"));
    }

    @Test
    public void multiIntersectMember() {
        System.out.println(redisUtil.addSet("set-three", "three", "eight", "seven"));
        Set<String> set = new HashSet<>();
        set.add("set-three");
        set.add("set-two");
        System.out.println(redisUtil.intersect("set-one", set));
    }

    @Test
    public void storeIntersectMember() {
        System.out.println(redisUtil.intersect("set-one", "set-two"));
        System.out.println(redisUtil.members("set-four"));
        System.out.println(redisUtil.storeIntersectMember("set-one", "set-two", "set-four"));
        System.out.println(redisUtil.members("set-four"));
    }

    @Test
    public void unionMember() {
        System.out.println(redisUtil.union("set-one", "set-two"));
    }

}