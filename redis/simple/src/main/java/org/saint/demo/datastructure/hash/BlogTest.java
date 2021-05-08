package org.saint.demo.datastructure.hash;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 7:17
 */
public class BlogTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public BlogTest() {
        jedis.auth("123456");
    }

    /**
     * 获取自增blog id
     *
     * @return
     */
    public long getBlogId() {
        return jedis.incr("blog_id_counter");
    }

    /**
     * 发布一个博客
     *
     * @param blog the information of blog
     * @return
     */
    public boolean publishBlog(Map<String, String> blog, String[] tags) {
        String id = blog.get("id");
        if (jedis.exists("article::" + id)) {
            return false;
        }
        blog.put("content_length", String.valueOf(blog.get("content").length()));
        jedis.sadd("article::" + id + "::tags", tags);
        jedis.hmset("article::" + id, blog);
        return true;

    }

    /**
     * 查看一篇博客
     *
     * @param id
     * @return
     */
    public Map<String, String> findBlogById(long id) {
        Map<String, String> blog = jedis.hgetAll("article::" + id);
        Set<String> tags = jedis.smembers("article::" + id + "::tags");
        blog.put("tags", tags.toString());
        incrementBlogViewCount(id);
        return blog;
    }

    /**
     * 更新一篇博客
     *
     * @param id
     * @param updatedBlog
     */
    public void updateBlog(long id, Map<String, String> updatedBlog) {
        String updatedContent = updatedBlog.get("content");
        if (!StringUtils.isEmpty(updatedContent)) {
            updatedBlog.put("content_length", String.valueOf(updatedContent.length()));
        }
        jedis.hmset("article::" + id, updatedBlog);
    }

    /**
     * 对博客进行点赞
     *
     * @param id
     */
    public void incrementBlogLikeCount(long id) {
        jedis.hincrBy("article::" + id, "like_count", 1);
    }

    /**
     * 增加博客浏览次数
     *
     * @param id
     */
    public void incrementBlogViewCount(long id) {
        jedis.hincrBy("article::" + id, "view_count", 1);
    }

    public static void main(String[] args) {
        BlogTest test = new BlogTest();
        // 发布一个博客
        long id = test.getBlogId();
        String content = "我爱早起，学习学习，Redis真牛逼！";
        Map<String, String> blog = new HashMap<>(16);
        blog.put("id", String.valueOf(id));
        blog.put("content", content);
        blog.put("title", "Redis大法");

        // 发布一篇博客
        test.publishBlog(blog, new String[]{"中间件", "Redis", "缓存"});

        // 别人浏览了你的博客
        Map<String, String> blogById = test.findBlogById(id);
        System.out.println("更新后的博客信息为： " + blogById);

        // 更新一篇博客
        Map<String, String> updatedBlog = new HashMap<>();
        updatedBlog.put("title", "Redis如何学习");
        updatedBlog.put("content", "学习Redis是一件很快乐的事情！");
        test.updateBlog(id, updatedBlog);

        // 博客被点赞了
        test.incrementBlogLikeCount(id);
        System.out.println("点赞后的博客信息为： " + test.findBlogById(id));
    }
}
