package com.open.redis.util.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 13:36
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long uuid;
    private Integer age;
}
