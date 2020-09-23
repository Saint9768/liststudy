package com.saint.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.saint.mapper.BlogMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;


/**
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-22 20:54
 */
public class MyBatisDemo {
    public static void main(String[] args) throws Exception {

        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        // 将XML文件加载进configuration中
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        System.out.println(mapper.selectBlog(1));

    }

    private static DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");

        return druidDataSource;
    }
}
