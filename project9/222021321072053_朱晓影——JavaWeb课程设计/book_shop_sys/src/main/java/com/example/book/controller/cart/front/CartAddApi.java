package com.example.book.controller.cart.front;

import com.example.book.pojo.Cart;
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

@WebServlet("/user/cart-insert-api")
public class CartAddApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String bookId = req.getParameter("bookId");
        String userId = req.getParameter("userId");

        Cart cart = new Cart();
        cart.setBookId(Integer.parseInt(bookId));
        cart.setUserId(Integer.parseInt(userId));

        CartServiceImpl cartService = new CartServiceImpl();
        try {
            cartService.insert(cart);
            resp.setContentType("application/json; charset=utf-8");
            try (ServletOutputStream writer = resp.getOutputStream()) {
                ApiSerialize.dumpsToJson2(writer, CommonResponse.success("添加成功"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            try (ServletOutputStream writer = resp.getOutputStream()) {
                ApiSerialize.dumpsToJson2(writer, CommonResponse.fail(500, "添加成功"));
            }
        }
    }
}
