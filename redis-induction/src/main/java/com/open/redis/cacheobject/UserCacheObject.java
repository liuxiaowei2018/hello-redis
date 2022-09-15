package com.open.redis.cacheobject;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月14日 16:15
 * @Description
 */
@Data
public class UserCacheObject {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
}
