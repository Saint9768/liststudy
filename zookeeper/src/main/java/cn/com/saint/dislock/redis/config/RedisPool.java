package cn.com.saint.dislock.redis.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-08 7:13
 */
public class RedisPool {

    /**
     * jedis连接池
     */
    private static JedisPool pool;

    /**
     * 最大连接数
     */
    private static final int maxTotal = 20;

    /**
     * 最大空闲连接数
     */
    private static final int maxIdle = 10;

    /**
     * 最小空闲连接数
     */
    private static final int minIdle = 5;

    /**
     * 在取连接时测试连接的可用性
     */
    private static final boolean testOnBorrow = true;

    /**
     * 再还连接时不测试连接的可用性
     */
    private static final boolean testOnReturn = false;

    static {
        //初始化连接池
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void close(Jedis jedis) {
        jedis.close();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, "120.26.187.17", 6379, 5000, "zn970816");
    }
}
