package org.saint.demo.datastructure.sortedset;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

/**
 * 自动补全案例
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-10 7:34
 */
public class AutoCompleteTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public AutoCompleteTest() {
        jedis.auth("123456");
    }

    /**
     * 对要自动补全的内容进行拆分、排列
     *
     * @param keyword
     */
    public void search(String keyword) {
        char[] keywordCharArray = keyword.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char keywordChar : keywordCharArray) {
            buffer.append(keywordChar);
            jedis.zincrby("potential_keywords::" + buffer.toString(), System.currentTimeMillis(), keyword);
        }
    }

    /**
     * 获取自动补全列表
     *
     * @param potentialKeyword
     * @return
     */
    public Set<String> getAutoCompleteList(String potentialKeyword) {
        return jedis.zrevrange("potential_keywords::" + potentialKeyword, 0, 2);
    }

    public static void main(String[] args) {
        AutoCompleteTest demo = new AutoCompleteTest();
        demo.search("我爱大家");
        demo.search("我喜欢学习Redis");
        demo.search("我喜欢学习Spark");
        demo.search("我很喜欢玩");
        demo.search("我不太喜欢睡觉");

        Set<String> autoCompleteList = demo.getAutoCompleteList("我");
        System.out.println("补全的列表为：" + autoCompleteList);

        autoCompleteList = demo.getAutoCompleteList("我喜");
        System.out.println("第二次补全的列表为：" + autoCompleteList);
    }

}
