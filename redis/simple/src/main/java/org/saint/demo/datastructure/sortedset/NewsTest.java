package org.saint.demo.datastructure.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 新闻浏览案例
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-10 7:05
 */
public class NewsTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public NewsTest() {
        jedis.auth("123456");
    }

    /**
     * 加入一篇新闻
     *
     * @param newsId
     */
    public void addNews(long newsId, long timestamp) {
        jedis.zadd("news", timestamp, String.valueOf(newsId));
    }

    /**
     * 分页获取指定分数之间的数据
     *
     * @param maxTimestamp the score of max
     * @param minTimestamp the score of min
     * @param index        start index
     * @param count        count of page
     * @return
     */
    public Set<Tuple> searchNews(long maxTimestamp, long minTimestamp, int index, int count) {
        return jedis.zrevrangeByScoreWithScores("news", maxTimestamp, minTimestamp, index, count);
    }

    public static void main(String[] args) {
        NewsTest demo = new NewsTest();
        for (int i = 0; i < 20; i++) {
            demo.addNews(i + 1, i + 1);
        }
        int max = 10;
        int min = 2;
        int pageSize = 5;
        int pageNo = 1;
        int startIndex = (pageNo - 1) * pageSize;
        Set<Tuple> tuples = demo.searchNews(max, min, startIndex, pageSize);
        System.out.println("第一页前五的新闻为：" + tuples);

    }

}
