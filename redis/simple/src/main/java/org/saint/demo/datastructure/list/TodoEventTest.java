package org.saint.demo.datastructure.list;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * OA系统的待办事项的管理案例
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-05-06 8:11
 */
public class TodoEventTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    /**
     * jedis客户端授权
     */
    public TodoEventTest() {
        jedis.auth("123456");
    }

    /**
     * 添加一个待办事项
     *
     * @param userId
     * @param todoEvent
     */
    public void addTodoEvent(long userId, String todoEvent) {
        jedis.lpush("todo_event::" + userId, todoEvent);
    }

    /**
     * 分页获取待办事项
     *
     * @param userId   user id
     * @param pageNo   the number of page
     * @param pageSize the size of page
     * @return
     */
    public List<String> findTodoEventByPage(long userId, int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageNo * pageSize - 1;
        return jedis.lrange("todo_event::" + userId, startIndex, endIndex);
    }

    /**
     * 指定位置插入待办事项
     *
     * @param userId          user id
     * @param listPosition    before of after
     * @param targetTodoEvent the target of todoEvent index
     * @param todoEvent       todoEvent content
     */
    public void insertTodoEvent(long userId, ListPosition listPosition, String targetTodoEvent, String todoEvent) {
        jedis.linsert("todo_event::" + userId, listPosition, targetTodoEvent, todoEvent);
    }

    /**
     * 完成一个待办事项
     *
     * @param userId    user id
     * @param todoEvent todoEvent
     */
    public void finishTodoEvent(long userId, String todoEvent) {
        jedis.lrem("todo_event::" + userId, 0, todoEvent);
    }

    /**
     * 修改一个待办事项
     *
     * @param userId
     * @param index
     * @param updatedTodoEvent
     */
    public void updateTodoEvent(long userId, int index, String updatedTodoEvent) {
        jedis.lset("todo_event::" + userId, index, updatedTodoEvent);
    }

    public static void main(String[] args) {
        TodoEventTest test = new TodoEventTest();
        // 添加20个待办事项
        for (int i = 0; i < 20; i++) {
            test.addTodoEvent(2, "the index of " + (i + 1) + " todoEvent");
        }

        // 分页查询
        int pageNo = 1;
        int pageSize = 7;
        List<String> todoEventByPage = test.findTodoEventByPage(2, pageNo, pageSize);
        System.out.println("第一次分页查询的结果为： " + todoEventByPage);

        // 插入一个数据
        String targetTodoEvent = todoEventByPage.get(2);
        test.insertTodoEvent(2, ListPosition.AFTER, targetTodoEvent, "在" + targetTodoEvent + "后随机插入一条数据");


        todoEventByPage = test.findTodoEventByPage(2, pageNo, pageSize);
        System.out.println("第二次分页查询的结果为： " + todoEventByPage);

        // 修改一个数据
        test.updateTodoEvent(2, 2, "修改了第三个数据");

        // 完成一个待办事项
        test.finishTodoEvent(2, todoEventByPage.get(0));

        todoEventByPage = test.findTodoEventByPage(2, pageNo, pageSize);
        System.out.println("第三次分页查询的结果为： " + todoEventByPage);

    }
}
