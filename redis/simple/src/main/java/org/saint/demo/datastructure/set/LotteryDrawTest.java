package org.saint.demo.datastructure.set;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 抽奖程序
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 14:42
 */
public class LotteryDrawTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public LotteryDrawTest() {
        jedis.auth("123456");
    }

    /**
     * 添加抽奖候选人
     *
     * @param userId             user id
     * @param lotteryDrawEventId lotteryDraw id
     */
    public void addLotteryDrawCandidate(long userId, long lotteryDrawEventId) {
        jedis.sadd("lottery_draw_event::" + lotteryDrawEventId, String.valueOf(userId));
    }

    /**
     * 抽奖,抽出count个人，如果set集合人数不足，可重复
     *
     * @param lotteryDrawEventId lotteryDraw event id
     * @param count              lotteryDraw count
     * @return
     */
    public List<String> doLotteryDraw(long lotteryDrawEventId, int count) {
        return jedis.srandmember("lottery_draw_event::" + lotteryDrawEventId, count);
    }

    /**
     * 随机抽奖
     *
     * @param lotteryDrawEventId
     * @return
     */
    public String doSingleLotteryDraw(long lotteryDrawEventId) {
        return jedis.spop("lottery_draw_event::" + lotteryDrawEventId);
    }
}
