package com.open.redis.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuxiaowei
 * @date 2022年11月03日 20:49
 * @Description
 * 二进制使用率: max_diff/(max-min)=85899345919/89999999999=95.4%
 */
@Slf4j
public class TaylorUtil {

    private static final long MIN = 10000000000L;

    // 11位后缀ID(17179869183)
    private static final long SUFFIX_ID = ((1L << 34) - 1);
    // 12位前缀ID(120259084288)
    private static final long PRE_ID = (7L << 34);
    // 最大ID(11位)
    private static final long MAX_ID = 85899345919L;


    public static long next(long id) {

        if (id < 0 || id > MAX_ID) {
            throw new IllegalArgumentException("Illegal id: " + id);
        }

        // 非线性
        id = swap(id, 7, 0);
        id = swap(id, 6, 1);

        // Id后34位
        long value = id & SUFFIX_ID;

        // Id后34位的最低八位和高1-9位交换
        value = (((value >> 8) | ((value & 255) << 26)));
        // Id前3位|Id后34位
        id = (id & PRE_ID) | value;
        // 加上最小值, 保证生成的11位
        id += MIN;
        return id;
    }

    private static long swap(long value, int x, int y) {
        return value & (~(1 << x)) & (~(1 << y)) | ((value >> x) & 1) << y | ((value >> y) & 1) << x;
    }
}
