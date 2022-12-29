package com.example.book.controller.book.backend;

import com.example.book.pojo.Book;
import com.example.book.service.BookServiceImpl;
import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/book-item-api")
public class BookItemApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BookServiceImpl bookService = new BookServiceImpl();
        Book book = new Book();
        try {
            book = bookService.getBookById(Integer.parseInt(req.getParameter("id")));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try (ServletOutputStream writer = resp.getOutputStream()) {
            resp.setContentType("application/json; charset=utf-8");
            ApiSerialize.dumpsToJson2(writer, CommonResponse.success(book));
        }
    }
}
