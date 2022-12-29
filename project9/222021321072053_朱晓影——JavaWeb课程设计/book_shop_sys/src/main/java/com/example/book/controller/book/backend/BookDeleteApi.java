package com.example.book.controller.book.backend;

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

@WebServlet("/admin/book-delete-api")
public class BookDeleteApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            bookService.deleteById(id);
        }
        catch (SQLException e) {
            e.printStackTrace();
            try(ServletOutputStream writer = resp.getOutputStream()) {
                resp.setContentType("application/json; charset=utf-8");
                ApiSerialize.dumpsToJson2(writer, CommonResponse.fail(500, "删除失败"));
            }
        }

        try(ServletOutputStream writer = resp.getOutputStream()) {
            resp.setContentType("application/json; charset=utf-8");
            ApiSerialize.dumpsToJson2(writer, CommonResponse.success("删除成功"));
        }
    }
}
