package org.saint.demo.datastructure.hash;

import redis.clients.jedis.Jedis;

/**
 * 网址点击追踪机制案例
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 6:38
 */
public class HashTest {

    private static final String[] X36_ARRAY = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * 给种子数据设置初始值
     */
    public HashTest() {
        jedis.auth("123456");
        jedis.set("short_url_seed", "20210501");
    }

    /**
     * 对种子数据进行对36的取余。
     *
     * @param url long url
     * @return
     */
    public String urlToShortUrl(String url) {
        Long shortUrlSeed = jedis.incr("short_url_seed");
        StringBuffer buffer = new StringBuffer();
        while (shortUrlSeed > 0) {
            buffer.append(X36_ARRAY[(int) (shortUrlSeed % 36)]);
            shortUrlSeed = shortUrlSeed / 36;
        }
        //反转字符串保证数据的递增性
        String shortUrl = buffer.reverse().toString();
        jedis.hset("short_url_access_count", shortUrl, "0");
        jedis.hset("short_url_mapping", shortUrl, url);
        return shortUrl;
    }

    /**
     * 根据短链接地址获取长链接地址
     *
     * @param shortUrl short url
     * @return
     */
    public String getUrlByShortUrl(String shortUrl) {
        return jedis.hget("short_url_mappiing", shortUrl);
    }

    /**
     * 增加获取短链接地址的访问次数
     *
     * @param shortUrl shortUrl
     */
    public void incrementShortUrlAccessCount(String shortUrl) {
        jedis.hincrBy("short_url_access_count", shortUrl, 1);
    }

    /**
     * 获取短链接地址的方式次数
     *
     * @param shortUrl shortUrl
     * @return long
     */
    public long getShortUrlAccessCount(String shortUrl) {
        return Long.valueOf(jedis.hget("short_url_access_count", shortUrl));
    }

    public static void main(String[] args) {
        HashTest test = new HashTest();
        String url = "http://www.baidu.com";
        String shortUrl = test.urlToShortUrl(url);

        System.out.println("短链接地址为：" + shortUrl);

        for (int i = 0; i < 152; i++) {
            test.incrementShortUrlAccessCount(shortUrl);
        }
        long shortUrlAccessCount = test.getShortUrlAccessCount(shortUrl);
        System.out.println("短链接地址获取次数为：" + shortUrlAccessCount);
    }
}

