package com.example.book.service;

import com.example.book.db.DBEngine;
import com.example.book.db.RecordVisitor;
import com.example.book.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserServiceImpl {
    // 查询user列表并且返回
    public List<User> getUserList() throws SQLException {  // 是否考虑线程安全？
        // 执行数据库查询操作
        String sqlString = "SELECT * FROM user;";

        return DBEngine.getInstance().query(sqlString, new RecordVisitor<User>() {
            @Override
            public User visit(ResultSet rs) throws SQLException {
               User user = new User();
               user.setId(rs.getInt("id"));
               user.setUserType(rs.getInt("userType"));
               user.setUsername(rs.getString("username"));

               return user;
            }
        });
    }

    // 添加记录
    public void insert(String username, String password, int userType)
            throws SQLException {
        String sqlTemplate = "insert into user(username, password, userType)"
                + "values(\"%s\", \"%s\", \"%s\");";
        String targetSql = String.format(sqlTemplate, username, password, userType);

        DBEngine.getInstance().execute(targetSql);
    }

    // 删除记录


    // 分页查询记录接口

    // 查询所有记录数
    public int count() throws SQLException {
        Connection connection = DBEngine.getInstance().getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) AS `records` FROM user";
        int count = 0;

        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()) {
            count = rs.getInt("records");
        }

        return count;
    }

    // 单条记录查询
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = \"%s\"";
        String targetSql = String.format(sql, username);
        Connection connection = DBEngine.getInstance().getConnection();
        Statement statement = connection.createStatement();

        User instance = null;

        ResultSet  rs = statement.executeQuery(targetSql);
        while(rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));

            instance = user;
        }

        return instance;
    }

    public User authUser(String username, String password) throws SQLException {
        User user = null;

        String sqlTemplate = "SELECT * FROM user WHERE username=\"%s\" AND password=\"%s\"";
        String targetSql = String.format(sqlTemplate, username, password);

        Connection connection = DBEngine.getInstance().getConnection();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(targetSql);
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUserType(rs.getInt("userType"));
            user.setUsername(rs.getString("username"));
        }

        return user;
    }
}
