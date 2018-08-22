/*package com.xuemi.elevator.visit.http.api;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xuemi.framework.base_util.util.Jedis.RedisUtil;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class SerializeUtil {
	
	private static final Logger log = LoggerFactory.getLogger(SerializeUtil.class);
	
	@Resource(name="centerShardedJedisPool")
    private ShardedJedisPool redisPool;
	
    public static ShardedJedisPool shardedJedisPool;  
    //通过该标签以及该方法实现给static属性注入  
    @PostConstruct
    public void init(){  
    	SerializeUtil.shardedJedisPool = redisPool;  
    }

    private static ShardedJedis getRedisClient() {
        try {
             ShardedJedis shardJedis = shardedJedisPool.getResource();
 
            return shardJedis;
        } catch (Exception e) {
        	System.out.println(e);
            log.error("getRedisClent error", e);
        }
        return null;
    }

    private static void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
    
    *//**
     * 普通的set方法
     * @param key
     * @param value
     *//*
    public static void set(String key, String value) {
        ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
    }
    
    *//**
     * 带超时时间的set方法
     * @param key
     * @param value
     * @param seconds
     * @return
     *//*
    public static void set(String key, String value, int seconds){
        ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
    }
    
    *//**
     * 存object的set方法
     * @param key
     * @param value
     *//*
    public static void setObject(String key, Object value){
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.set(key.getBytes(), RedisUtil.serialize(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
    }
    
    *//**
     * 带过期时间的存object的set方法
     * @param key
     * @param value
     * @param seconds
     *//*
    public static void setObject(String key, Object value, int seconds){
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.setex(key.getBytes(), seconds, RedisUtil.serialize(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
    }
    
    *//**
     * 普通的get方法
     * @param key
     * @return
     *//*
    public static String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = shardedJedis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
    
    *//**
     * 获取object的get方法
     * @param key
     * @return
     *//*
    public static Object getObject(String key){
     	Object result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
 
        boolean broken = false;
        try {
            byte[] temp = shardedJedis.get(key.getBytes());
            if(temp==null){
            	return null;
            }
            result = RedisUtil.unserialize(temp);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
 
        return result;
    }
    
    *//**
     * 删除
     * @param key
     * @return
     *//*
    public static long del(String key){
    	boolean broken = false;
    	ShardedJedis shardedJedis = getRedisClient();
    	long result = 0;
        try {
        	result = shardedJedis.del(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
    
    
    *//**
     * 判断是否存在该key
     * @param key
     * @return
     *//*
    public static Boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
	
    *//**
     * 重新设置key的过期时间
     * @param key
     * @param seconds
     *//*
    public static void expire(String key, int seconds){
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
    }
    
    *//**
     * 向列表尾部添加
     *//*
    public static long rpush(String key,String... value){
    	long result = 0L;
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            return shardedJedis.rpush(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
    
    *//**
     * 列表头部弹出
     *//*
    public static String lpop(String key){
    	String result = "";
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            return shardedJedis.lpop(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
    
    *//**
     * 增量
     *//*
    public static long increase(String key){
    	long result = 0L;
    	ShardedJedis shardedJedis = getRedisClient();
        boolean broken = false;
        try {
            return shardedJedis.incr(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }
    
//    /**
//     * 为列表添加object元素
//     * @param key
//     * @param value
//     */
//    public static void addObjToList(String key, Object value){
//    	ShardedJedis shardedJedis = getRedisClient();
//        boolean broken = false;
//        try {
//            shardedJedis.lpushx(key.getBytes(), RedisUtil.serialize(value));
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            broken = true;
//        } finally {
//            returnResource(shardedJedis, broken);
//        }
//    }
//    
//    /**
//     * 
//     * @param key
//     */
//    public static <T> List<T> listAll(String key){
//    	ShardedJedis shardedJedis = getRedisClient();
//        boolean broken = false;
//        List<T> result = new ArrayList<T>();
//        try {
//        	int length = shardedJedis.llen(key.getBytes()).intValue();
//        	List<byte[]> tempList = shardedJedis.lrange(key.getBytes(), 0, length);
//        	for (byte[] bs : tempList) {
//        		result.add((T) RedisUtil.unserialize(bs));
//			}
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            broken = true;
//        } finally {
//            returnResource(shardedJedis, broken);
//        }
//        return result;
//    }
    
//}
 