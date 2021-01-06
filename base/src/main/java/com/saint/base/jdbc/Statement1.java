package com.saint.base.jdbc;

import com.saint.base.util.JdbcUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-05 21:02
 */
public class Statement1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Statement1.class);
    private Statement stat;
    private PreparedStatement pst;
    private ResultSet rs;

    /**
     * 1. 插入一条数据，并获取到自增的主键
     */
    public void insert(Connection conn) throws SQLException {
        String insertSql = "insert into user(name,age) values(?,?)";
        pst = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, "lucy");
        pst.setInt(2, 32);
        pst.executeUpdate();
        // 获取自动生成的主键
        rs = pst.getGeneratedKeys();
        while (rs.next()) {
            int generatedKey = rs.getInt(1);
            System.out.println(generatedKey);
        }
    }

    /**
     * 查询数据
     */
    public void listData(Connection conn) throws SQLException {
        String sql = "select id,name,age from user";
        rs = stat.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println(id + " " + name + " " + age);
        }
    }

    /**
     * 更新数据
     */
    public void update(Connection conn) throws SQLException {
        String updateSql = "update user set name=? where id = ?";
        pst = conn.prepareStatement(updateSql);
        pst.setString(1, "bobDog");
        pst.setInt(2, 1);
        int updateRes = pst.executeUpdate();
        if (updateRes > 0) {
            System.out.println("更新成功！");
        }
    }

    /**
     * 删除数据
     */
    public void delete(Connection conn) throws Exception {
        String deleteSql = "delete from user where id=?";
        pst = conn.prepareStatement(deleteSql);
        pst.setInt(1, 1);
        int deleteRes = pst.executeUpdate();
        if (deleteRes > 0) {
            System.out.println("删除成功");
        }
    }

    /**
     * 批量插入数据
     */
    public void batchInsert(Connection conn) throws Exception {
        User user1 = new User(3, "test3", 1);
        User user2 = new User(4, "4test", 2);
        User user3 = new User(5, "hello", 3);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        String addBatchSql = "insert into user(id,name,age) values(?,?,?)";
        pst = conn.prepareStatement(addBatchSql);
        for (User user : users) {
            pst.setInt(1, user.getId());
            pst.setString(2, user.getName());
            pst.setInt(3, user.getAge());
            pst.addBatch();
        }
        int[] res = pst.executeBatch();
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) throws Exception {

        Statement1 object = new Statement1();
        // 1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn;
        // 2.建立连接
        conn = JdbcUtil.getConnection();
        // 3. 创建statement对象执行SQL
        // 4. 获取结果
        object.listData(conn);
        object.insert(conn);
        object.update(conn);
        object.delete(conn);
        object.batchInsert(conn);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private int id;
    private String name;
    private int age;
}
