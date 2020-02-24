package com.gen.mmall.common;

import com.gen.mmall.common.config.RedisConfig;
import com.gen.mmall.util.PropUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class RedisPool {

    private static Properties props = PropUtil.getProperties("application.properties");

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.valueOf(props.getProperty("redis.max.total")); //最大连接数
    private static Integer maxIdle = Integer.valueOf(props.getProperty("redis.max.idle"));//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.valueOf(props.getProperty("redis.min.idle"));//在jedispool中最小的idle状态(空闲的)的jedis实例的个数

    private static Boolean testOnBorrow = Boolean.valueOf(props.getProperty("redis.test.borrow"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.valueOf(props.getProperty("redis.test.return"));//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    private static String redisIp = props.getProperty("redis.ip");
    private static Integer redisPort = Integer.valueOf(props.getProperty("redis.port"));


    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        pool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static{
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    /**
     * jedis 3.0 版本后启用下面两个方法，释放资源方式为直接调用close方法
     * Jedis jedis = pool.getResource();
     * ---业务操作
     * jedis.close();
     *
     */
    public static void returnBrokenResource(Jedis jedis){
//        pool.returnBrokenResource(jedis);
        jedis.close();
    }

    public static void returnResource(Jedis jedis){
//        pool.returnResource(jedis);
        jedis.close();
    }


    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("sdgv","geelyvalue");
        jedis.close();

        pool.destroy();//临时调用，销毁连接池中的所有连接
        System.out.println("program is end");
    }

}
