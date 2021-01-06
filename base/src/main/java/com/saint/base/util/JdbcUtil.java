package com.saint.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-07 6:42
 */
public class JdbcUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtil.class);
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql//127.0.0.1:3306/test";
        username = "root";
        password = "123456";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载数据库驱动异常！", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭数据库连接
     *
     * @param conn 数据库连接
     * @param stat statement对象
     * @param rs   结果集
     */
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                LOGGER.error("关闭结果集连接异常！", e);
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (Exception e) {
                LOGGER.error("关闭Statement异常！", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                LOGGER.error("关闭数据库连接异常！", e);
            }
        }
    }
}
