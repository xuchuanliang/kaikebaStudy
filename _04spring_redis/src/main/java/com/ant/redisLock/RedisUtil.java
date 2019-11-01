package com.ant.redisLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * redis分布式锁工具类
 */
public class RedisUtil {
    private static JedisPool jedisPool;
    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        jedisPool = new JedisPool(config,"192.168.196.128",6379,1000*5,"hkbt@123");
    }

    /**
     * 使用jedis-redis获取分布式锁
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public static boolean getLock(String lockKey,String requestId,int expireTime){
        String s = jedisPool.getResource().set(lockKey, requestId, "NX", "EX", expireTime);
        return "OK".equalsIgnoreCase(s);
    }

    public static boolean getLockV2(String key,String value,int expireTime){
        Lock lock = new ReentrantLock();
        lock.tryLock();
        try {
            Long l = jedisPool.getResource().setnx(key, value);
            if(1==l){
                jedisPool.getResource().expire(key,expireTime);
                return true;
            }
            return false;
        }finally {
            lock.unlock();
        }
    }

    public static boolean unLock(String key,String value){
        if(StringUtils.isNoneBlank(value) && value.equalsIgnoreCase(jedisPool.getResource().get(key))){
            jedisPool.getResource().del(key);
            return true;
        }
        return false;
    }

    public static boolean unLockV2(String key,String value){
        String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Object result = jedisPool.getResource().eval(script, Collections.singletonList(key),Collections.singletonList(value));
        return result.equals(1L);
    }

}
