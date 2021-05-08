package org.saint.demo.datastructure.set;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 商品搜索
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 14:54
 */
public class ProductSearchTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public ProductSearchTest() {
        jedis.auth("123456");
    }

    /**
     * 添加商品的时候附带一些关键词
     *
     * @param productId
     * @param keywords
     */
    public void addProduct(long productId, String[] keywords) {
        for (String keyword : keywords) {
            jedis.sadd("keyword::products::" + keyword, String.valueOf(productId));
        }
    }

    /**
     * 找出包含多个关键字的商品
     *
     * @param keywords
     * @return
     */
    public Set<String> searchProduct(String[] keywords) {
        List<String> keywordSetKeys = new ArrayList<>();
        for (String keyword : keywords) {
            keywordSetKeys.add("keyword::products::" + keyword);
        }
        String[] keywordArray = keywordSetKeys.toArray(new String[keywordSetKeys.size()]);
        return jedis.sinter(keywordArray);
    }

    public static void main(String[] args) {
        ProductSearchTest demo = new ProductSearchTest();

        // 添加一批商品
        demo.addProduct(11, new String[]{"手机", "iphone", "潮流"});
        demo.addProduct(12, new String[]{"iphone", "潮流", "炫酷"});
        demo.addProduct(13, new String[]{"iphone", "天蓝色"});

        // 根据关键词搜索商品
        Set<String> searchResult = demo.searchProduct(new String[]{"iphone", "潮流"});
        System.out.println("商品搜索结果为：" + searchResult);
    }

}
