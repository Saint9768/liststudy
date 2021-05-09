package org.saint.demo.datastructure.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-09 14:14
 */
public class MusicRankingListTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public MusicRankingListTest() {
        jedis.auth("123456");
    }

    /**
     * 添加歌曲
     *
     * @param songId
     */
    public void addSong(long songId) {
        jedis.zadd("music_ranking_list", 0, String.valueOf(songId));
    }

    /**
     * 增加歌曲的分数
     *
     * @param songId
     * @param score
     */
    public void incrementSongScore(long songId, double score) {
        jedis.zincrby("music_ranking_list", score, String.valueOf(songId));
    }

    /**
     * 获取歌曲排名
     *
     * @param songId
     * @return
     */
    public long getSongRank(long songId) {
        return jedis.zrevrank("music_ranking_list", String.valueOf(songId));
    }

    /**
     * 获取音乐排行榜
     *
     * @return
     */
    public Set<Tuple> getMusicRankingList(int start, int end) {
        return jedis.zrevrangeWithScores("music_ranking_list", start, end);
    }

    public static void main(String[] args) {
        MusicRankingListTest demo = new MusicRankingListTest();
        for (int i = 0; i < 20; i++) {
            demo.addSong(i + 1);
        }

        demo.incrementSongScore(5, 3.2);
        demo.incrementSongScore(15, 5.6);
        demo.incrementSongScore(7, 9.6);

        long songRank = demo.getSongRank(5);
        System.out.println("歌曲5当前的排名是：" + (songRank + 1));

        Set<Tuple> musicRankingList = demo.getMusicRankingList(0, 2);
        System.out.println("查看音乐排行榜前三的歌曲：" + musicRankingList);
    }


}
