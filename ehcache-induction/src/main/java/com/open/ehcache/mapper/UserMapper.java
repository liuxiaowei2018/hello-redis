package com.open.ehcache.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.open.ehcache.dataobject.UserDO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author liuxiaowei
 * @date 2022年09月16日 14:03
 * @Description
 */
@Repository
@CacheConfig(cacheNames = "users")
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 优先读缓存
     *
     * @param id
     * @return com.open.ehcache.dataobject.UserDO
     * @date 2022/9/16 14:04
     */
    @Cacheable(key = "#id")
    UserDO selectById(Integer id);

    /**
     * 主动写缓存
     *
     * @param user
     * @return com.open.ehcache.dataobject.UserDO
     * @date 2022/9/16 14:04
     */
    @CachePut(key = "#user.id")
    default UserDO insert0(UserDO user) {
        // 插入记录
        this.insert(user);
        // 返回用户
        return user;
    }

    /**
     * 删除缓存
     *
     * @param id
     * @return int
     * @date 2022/9/16 14:04
     */
    @CacheEvict(key = "#id")
    int deleteById(Integer id);
}
