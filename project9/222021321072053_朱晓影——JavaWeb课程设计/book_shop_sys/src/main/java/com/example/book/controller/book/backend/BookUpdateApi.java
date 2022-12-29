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

@WebServlet("/admin/book-update-api")
public class BookUpdateApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BookServiceImpl bookService = new BookServiceImpl();
        Book book = new Book();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String author = req.getParameter("author");
        String publisher = req.getParameter("publisher");

        book.setName(name); book.setPrice(Double.parseDouble(price));
        book.setId(Integer.parseInt(id));
        book.setDescription(description);
        book.setAuthor(author);
        book.setPublisher(publisher);

        try {
            bookService.update(book);
        }
        catch (SQLException e) {
            e.printStackTrace();
            resp.setContentType("application/json; charset=utf-8");
            try (ServletOutputStream writer = resp.getOutputStream()) {
                ApiSerialize.dumpsToJson2(writer, CommonResponse.fail(500,"更新失败"));
            }
        }

        resp.setContentType("application/json; charset=utf-8");
        try (ServletOutputStream writer = resp.getOutputStream()) {
            ApiSerialize.dumpsToJson2(writer, CommonResponse.success("更新成功"));
        }
    }
}
