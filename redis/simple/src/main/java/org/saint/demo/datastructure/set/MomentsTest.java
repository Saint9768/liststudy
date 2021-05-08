package org.saint.demo.datastructure.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 朋友圈点赞功能
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 14:23
 */
public class MomentsTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public MomentsTest() {
        jedis.auth("123456");
    }

    /**
     * 点赞
     *
     * @param userId   user id
     * @param momentId article id
     */
    public void likeMoment(long userId, long momentId) {
        jedis.sadd("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 取消点赞
     *
     * @param userId
     * @param momentId
     */
    public void dislikeMoment(long userId, long momentId) {
        jedis.srem("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 是否给某条朋友圈点过赞
     *
     * @param userId
     * @param momentId
     * @return
     */
    public boolean hasLikeMoment(long userId, long momentId) {
        return jedis.sismember("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 获取某条朋友圈所有点赞的人
     *
     * @param momentId
     * @return
     */
    public Set<String> getMomentLikeUsers(long momentId) {
        return jedis.smembers("moment_like_users::" + momentId);
    }

    /**
     * 获取某条朋友圈被点赞的次数
     *
     * @param momentId
     * @return
     */
    public long getMomentLikeUsersCount(long momentId) {
        return jedis.scard("moment_like_users::" + momentId);
    }


    public static void main(String[] args) {
        MomentsTest test = new MomentsTest();
        long userId = 11;
        long momentId = 151;
        long friendId = 12;
        long otherFriendId = 13;

        test.likeMoment(friendId, momentId);
        test.dislikeMoment(friendId, momentId);
        boolean hasLikeMoment = test.hasLikeMoment(friendId, momentId);
        System.out.println("朋友1刷朋友圈，他是否对你的朋友圈点赞过： " + (hasLikeMoment ? "是" : "否"));

        test.likeMoment(otherFriendId, momentId);
        boolean hasLikeMoment2 = test.hasLikeMoment(friendId, momentId);
        System.out.println("朋友2刷朋友圈，他是否对你的朋友圈点赞过： " + (hasLikeMoment2 ? "是" : "否"));

        long momentLikeUsersCount = test.getMomentLikeUsersCount(momentId);
        Set<String> momentLikeUsers = test.getMomentLikeUsers(momentId);
        System.out.println("你的朋友圈被 " + momentLikeUsersCount + " 个人赞过！ 点赞的用户为： " + momentLikeUsers);

    }
}
