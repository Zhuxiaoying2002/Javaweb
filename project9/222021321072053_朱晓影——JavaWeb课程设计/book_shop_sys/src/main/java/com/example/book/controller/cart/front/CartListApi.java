package com.example.book.controller.cart.front;

import com.example.book.pojo.Book;
import com.example.book.service.BookServiceImpl;
import com.example.book.service.CartServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/user/cart-list-api")
public class CartListApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取数据
        String userId = req.getParameter("userId");
        CartServiceImpl cartService = new CartServiceImpl();
        List<Map<String, Object>> bookList = new ArrayList<>();
        try {
            bookList = cartService.select(Integer.parseInt(userId));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回数据
        try (ServletOutputStream writer = resp.getOutputStream()) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json; charset=utf-8");
            ApiSerialize.dumpsToJson2(writer, CommonResponse.success(bookList));
        }
    }
}
