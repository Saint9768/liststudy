package org.saint.demo.datastructure.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 推荐其他商品案例
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-10 7:14
 */
public class RecommendProductTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public RecommendProductTest() {
        jedis.auth("123456");
    }

    /**
     * 继续购买商品
     *
     * @param productId
     * @param otherProductId
     */
    public void continuePurchase(long productId, long otherProductId) {
        jedis.zincrby("continue_purchase_products::" + productId, 1, String.valueOf(otherProductId));
    }

    /**
     * 推荐其他人购买过的其他商品
     *
     * @param productId
     * @return
     */
    public Set<Tuple> getRecommendProducts(long productId) {
        return jedis.zrevrangeWithScores("continue_purchase_products::" + productId, 0, 2);
    }

    public static void main(String[] args) throws Exception {
        RecommendProductTest demo = new RecommendProductTest();

        int productId = 1;

        for (int i = 0; i < 20; i++) {
            demo.continuePurchase(productId, i + 2);
        }
        for (int i = 0; i < 3; i++) {
            demo.continuePurchase(productId, i + 2);
        }

        Set<Tuple> recommendProducts = demo.getRecommendProducts(productId);
        System.out.println("推荐其他人购买过的商品：" + recommendProducts);
    }

}
