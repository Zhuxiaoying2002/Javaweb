package com.example.book.service;

import com.example.book.db.DBEngine;
import com.example.book.pojo.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl {
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

    public void insert(Book book) throws SQLException {
        String sqlTemplate = "insert into book(name, imgUrl, author, publisher, description, price)"
                + "values(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")";

        String sqlTarget = String.format(sqlTemplate,
                book.getName(),
                book.getImgUrl(),
                book.getAuthor(),
                book.getPublisher(),
                book.getDescription(),
                book.getPrice());

        db.execute(sqlTarget);
    }

    public void update(Book book) throws SQLException {
        String sqlTemplate = "UPDATE book "
                + "SET name=\"%s\", author=\"%s\", publisher=\"%s\", description=\"%s\", price=\"%s\" "
                + "WHERE id=\"%s\";";

        String sqlTarget = String.format(sqlTemplate,
                book.getName(),
                book.getAuthor(),
                book.getPublisher(),
                book.getDescription(),
                book.getPrice(),
                book.getId());

        db.execute(sqlTarget);
    }

    public List<Book> bookList() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sqlTemplate = "SELECT * FROM book;";
        statement = connection.createStatement();
        rs = statement.executeQuery(sqlTemplate);

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setImgUrl(rs.getString("imgUrl"));
            book.setPrice(rs.getDouble("price"));
            book.setDescription(rs.getString("description"));
            book.setPublisher(rs.getString("publisher"));
            book.setAuthor(rs.getString("author"));
            book.setName(rs.getString("name"));
            books.add(book);
        }

        return books;
    }

    public List<Book> bookList(String name) throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book WHERE name LIKE " + "'%" + name + "%'";
        statement = connection.createStatement();
        rs = statement.executeQuery(sql);

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setImgUrl(rs.getString("imgUrl"));
            book.setPrice(rs.getDouble("price"));
            book.setDescription(rs.getString("description"));
            book.setPublisher(rs.getString("publisher"));
            book.setAuthor(rs.getString("author"));
            book.setName(rs.getString("name"));
            books.add(book);
        }

        return books;
    }

    public void deleteById(int id) throws SQLException {
        String sqlTemplate = "DELETE FROM book WHERE id=\"%s\";";
        String targetSql = String.format(sqlTemplate, id);

        db.execute(targetSql);
    }

    public Book getBookById(int id) throws SQLException {
        Book book = null;
        String sqlTemplate = "SELECT * FROM book WHERE id=\"%s\";";
        String targetSql = String.format(sqlTemplate, id);

        statement = connection.createStatement();
        rs = statement.executeQuery(targetSql);

        while (rs.next()) {
            book = new Book();
            book.setId(rs.getInt("id"));
            book.setImgUrl(rs.getString("imgUrl"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            book.setPublisher(rs.getString("publisher"));
            book.setPrice(rs.getDouble("price"));
            book.setName(rs.getString("name"));
        }

        return book;
    }
}
