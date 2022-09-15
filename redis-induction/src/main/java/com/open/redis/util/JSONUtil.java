package com.open.redis.util;

import com.alibaba.fastjson.JSON;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:15
 * @Description
 */
public class JSONUtil {

    public static  <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static String toJSONString(Object javaObject) {
        return JSON.toJSONString(javaObject);
    }

    public static byte[] toJSONBytes(Object javaObject) {
        return JSON.toJSONBytes(javaObject);
    }
}
