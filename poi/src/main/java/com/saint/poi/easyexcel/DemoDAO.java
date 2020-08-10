package com.saint.poi.easyexcel;

import java.util.List;

/**
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-08-11 7:18
 */
public class DemoDAO {
    public void save(List<DemoData> list) {
        //这里做持久化操作
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}
