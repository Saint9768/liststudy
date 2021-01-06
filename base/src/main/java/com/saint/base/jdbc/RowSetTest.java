package com.saint.base.jdbc;

import com.saint.base.util.JdbcUtil;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-07 7:18
 */
public class RowSetTest {

    public static void main(String[] args) throws SQLException {

        // 获取行集
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = factory.createCachedRowSet();

        // 1. 使用结果集来填充CachedRowSet对象
        Connection conn = JdbcUtil.getConnection();
        Statement stat = conn.createStatement();
        String sql = "select id,name,age from user";
        ResultSet rs = stat.executeQuery(sql);
        cachedRowSet.populate(rs);
        // 现在可以自由关闭数据库的连接。
        JdbcUtil.close(conn, stat, rs);

        // 2. 自己创建一个数据库连接
        cachedRowSet.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        cachedRowSet.setUsername("root");
        cachedRowSet.setPassword("123456");
        // 2.1 设置语句和所有参数
        cachedRowSet.setCommand("select * from user where id > ?");
        cachedRowSet.setInt(1, 4);
        // 2.2 将所有查询结果填充到行集中
        cachedRowSet.execute();
        while (cachedRowSet.next()) {
            System.out.println(cachedRowSet.getString(2));
        }
        // 2.2.2)分页获取行集
//        cachedRowSet.setPageSize(10);
//        cachedRowSet.execute();
//        // 获取下一页
//        cachedRowSet.nextPage();
        // 提交行集中修改了的数据
//        cachedRowSet.acceptChanges(conn);
//        cachedRowSet.acceptChanges();
    }
}
