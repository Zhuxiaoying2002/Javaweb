package com.example.book.service;

import com.example.book.db.DBEngine;
import com.example.book.pojo.Cart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl {
    private static DBEngine db = DBEngine.getInstance();
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    static {
        try {
            connection = db.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Cart cart) throws SQLException {
        String sqlTemplate = "INSERT INTO cart (bookId, userId, quantity, total)"
                + "VALUES(\"%S\", \"%S\", 1, 99);";
        String sqlTarget = String.format(sqlTemplate,
                cart.getBookId(),
                cart.getUserId());

        db.execute(sqlTarget);
    }

    public List<Map<String, Object>> select(int userId) throws SQLException {
        String sql = "SELECT \n" +
                "\tcart.id as `id`, book.name as `name`, book.author as `author`, book.publisher as `publisher`, book.description as `description`, book.price as `price`, user.username as `user`\n" +
                "FROM book, user, cart WHERE book.id = cart.bookId AND user.id = cart.userId AND user.id = \"%s\";";
        String target = String.format(sql, userId);

        statement = connection.createStatement();
        rs = statement.executeQuery(target);

        List<Map<String, Object>> result = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", rs.getInt("id"));
            map.put("name", rs.getString("name"));
            map.put("author", rs.getString("author"));
            map.put("publisher", rs.getString("publisher"));
            map.put("description", rs.getString("description"));
            map.put("price", rs.getDouble("price"));
            map.put("user", rs.getString("user"));

            result.add(map);
        }

        return result;
    }

    public void deleteByCartId(int id) throws SQLException {
        String sql = "DELETE FROM cart WHERE id = \"%s\"";
        String target = String.format(sql, id);

        db.execute(target);
    }
}
