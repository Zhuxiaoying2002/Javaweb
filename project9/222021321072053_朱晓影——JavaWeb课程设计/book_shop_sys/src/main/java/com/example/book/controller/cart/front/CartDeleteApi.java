package com.example.book.controller.cart.front;

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

@WebServlet("/front/cart-delete-api")
public class CartDeleteApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cartId = req.getParameter("cartId");

        CartServiceImpl cartService = new CartServiceImpl();
        resp.setContentType("application/json; charset=utf-8");
        try {
            cartService.deleteByCartId(Integer.parseInt(cartId));
        }
        catch (SQLException e) {
            e.printStackTrace();
            try (ServletOutputStream w = resp.getOutputStream()) {
                ApiSerialize.dumpsToJson2(w, CommonResponse.fail(500, "删除失败"));
            }
        }

        try (ServletOutputStream w = resp.getOutputStream()) {
            ApiSerialize.dumpsToJson2(w, CommonResponse.success("删除成功"));
        }
    }
}
