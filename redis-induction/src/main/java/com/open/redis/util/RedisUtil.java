package com.open.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxiaowei
 * @date 2022年10月20日 12:18
 * @Description
 */
@Slf4j
@Component
public class RedisUtil {

    /**
     * Spring Redis Template
     */
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 查询 keyPatten* 的所有 key
     *
     * @param keyPatten the key patten
     * @return Set
     */
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.keys(keyPatten + "*");
    }

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除key
     *
     * @param keys the keys
     * @return the long
     */
    public boolean del(final String... keys) {
        boolean result = false;
        for (String key : keys) {
            result = redisTemplate.delete(key);
        }
        return result;
    }

    /**
     * 修改redis中key的名称
     *
     * @param oldKey
     * @param newKey
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }


    /**
     * 设置过期时间
     *
     * @param key the key
     * @return Boolean
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }


    /**
     * 批量删除key
     *
     * @param keys Collection<K> keys
     * @return the long
     */
    public long del(final List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 将当前传入的key值序列化为byte[]类型
     *
     * @param key
     * @return the long
     */
    public byte[] dump(final String key) {
        return redisTemplate.dump(key);
    }

    // ------------------------- String操作 ----------------------------

    /**
     * 根据 key 获取对象
     *
     * @param key the key
     * @return String
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 添加到缓存
     *
     * @param key   the key
     * @param value the value
     */
    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加带有过期时间的缓存
     *
     * @param key      redis主键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 过期时间单位
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 对某个主键对应的值加一
     * value值必须是全数字的字符串
     *
     * @param key the key
     * @return the long
     */
    public long incr(final String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    // ------------------------- Hash操作 ----------------------------

    /**
     * 对HashMap操作(新增key-value)
     *
     * @param key       the key
     * @param hashKey   the hash key
     * @param hashValue the hash value
     */
    public void putHashValue(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 获取单个field对应的值
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the hash values
     */
    public Object getHashValues(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    public void delHashValues(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 根据 key 值匹配 map
     *
     * @param key the key
     * @return the hash value
     */
    public Map<String, Object> getHashValue(String key) {
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        return operations.entries(key);
    }

    /**
     * 根据 key 值 批量添加
     *
     * @param key the key
     * @param map the map
     */
    public void putHashValues(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    // ------------------------- List操作 ----------------------------

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 批量存储
     *
     * @param key  the key
     * @param list the list
     * @return the long
     */
    public Long leftPushAll(String key, List<String> list) {
        return redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * 将值 value 插入到列表 key 当中，位于值 index 之前或之后
     * 默认之后
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void insert(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除并返回列表 key 的头元素
     *
     * @param key the key
     * @return the string
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long in(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 移除并返回列表 key 的末尾元素
     *
     * @param key the key
     * @return the string
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 返回列表 key 的长度
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    public Long length(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    public void remove(String key, long i, Object value) {
        redisTemplate.opsForList().remove(key, i, value);
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void set(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public List<Object> getList(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // ------------------------- Set操作 ----------------------------

    /**
     * set中增加元素，支持一次增加多个元素，逗号分隔即可
     * 结果返回添加的个数
     *
     * @param key
     * @param value
     * @return
     */
    public Long addSet(String key, Object... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * set中移除指定元素
     * key和value能查询到，返回1 移除成功
     * key不存在 或 存在对应的key，value不存在，返回0 移除失败
     *
     * @param key
     * @param value
     * @return 1 || 0
     */
    public Long removeSet(String key, Object value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 计算set集合大小
     *
     * @param key
     * @return
     */
    public Long countSet(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断set中是否存在某元素
     *
     * @param key
     * @param value
     * @return true || false
     */
    public Boolean hasMemberSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 随机获取set中的一个元素
     *
     * @param key
     * @return
     */
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取指定个数的元素（可能重复）
     *
     * @param key
     * @param num
     * @return
     */
    public List<Object> randomMembers(String key, Integer num) {
        return redisTemplate.opsForSet().randomMembers(key, num);
    }

    /**
     * 随机获取集合内指定个数的元素（不重复）
     *
     * @param key
     * @param num
     * @return
     */
    public Set<Object> distinctRandomMembers(String key, Integer num) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, num);
    }

    /**
     * 随机移除一个元素
     *
     * @param key
     * @return
     */
    public Object popMember(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取key下的所有元素
     *
     * @param key
     * @return
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 将key1集合中的元素value移到key2集合中
     *
     * @param key1
     * @param value
     * @param key2
     * @return
     */
    public Boolean moveMember(String key1, Object value, String key2) {
        return redisTemplate.opsForSet().move(key1, value, key2);
    }

    /**
     * 两个key之间取交集
     *
     * @param key
     * @param key2
     * @return
     */
    public Set<Object> intersect(String key, String key2) {
        return redisTemplate.opsForSet().intersect(key, key2);
    }

    /**
     * 一个集合与多个集合之间取交集
     *
     * @param key
     * @param key2
     * @return
     */
    public Set<Object> intersect(String key, Set<String> key2) {
        return redisTemplate.opsForSet().intersect(key, key2);
    }

    /**
     * 取交集并放入key3集合
     *
     * @param key1
     * @param key2
     * @param key3
     */
    public Long storeIntersectMember(String key1, String key2, String key3) {
        return redisTemplate.opsForSet().intersectAndStore(key1, key2, key3);
    }

    /**
     * 集合求并集
     *
     * @param key1
     * @param key2
     * @return
     */
    public Set<Object> union(String key1, String key2) {
        return redisTemplate.opsForSet().union(key1, key2);
    }

    /**
     * 求并集（一个集合与多个集合之间）
     *
     * @param key1
     * @param keys
     * @return
     */
    public Set<Object> multiUnionMember(String key1, Set<String> keys) {
        return redisTemplate.opsForSet().union(key1, keys);
    }

    // ------------------------- ZSet操作 ----------------------------

    /**
     * 增加有序集合
     * key和value存在新增失败，返回false；不存在新增成功，返回true。
     *
     * @param key
     * @param value
     * @param seqNo 分数
     * @return
     */
    public Boolean addZSet(String key, Object value, double seqNo) {
        return redisTemplate.opsForZSet().add(key, value, seqNo);
    }

    /**
     * 获取ZSet集合数量
     * key不存在，返回0；存在，返回集合的个数。
     *
     * @param key
     * @return
     */
    public Long countZSet(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取ZSet指定范围内的集合
     * key存在，返回集合的对应位置的元素，区间左开右闭。start从0开始，end传-1表示查询所有。
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> rangeZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 根据key和value移除指定元素
     * 未查询到对应的key和value，返回0，否则返回1
     * @param key
     * @param value
     * @return
     */
    public Long removeZSet(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取对应key和value的score
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 指定范围内元素排序
     *
     * @param key
     * @param v1
     * @param v2
     * @return
     */
    public Set<Object> rangeByScore(String key, double v1, double v2) {
        return redisTemplate.opsForZSet().rangeByScore(key, v1, v2);
    }

    /**
     * 指定元素增加指定值
     * key和value未查询到对应的元素，会创建key及对应的value，返回的score为传入的score；
     * key和value存在，则直接返回修改后的score
     * 减score直接传入负数即可。
     * @param key
     * @param obj
     * @param score 增加的分数值
     * @return
     */
    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    /**
     * 排名(元素在集合内对应的排名)
     * key和value未查询到对应的元素，返回null；存在，则直接返回元素对应的位置，从0开始，返回0，表示在第一位。
     * @param key
     * @param obj
     * @return
     */
    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }

}
