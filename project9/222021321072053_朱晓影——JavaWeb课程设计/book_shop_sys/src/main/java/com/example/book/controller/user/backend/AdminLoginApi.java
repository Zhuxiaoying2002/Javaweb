package com.example.book.controller.user.backend;

import com.example.book.controller.user.front.UserLoginApi;
import com.example.book.pojo.User;
import com.example.book.service.UserServiceImpl;
import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/super-role-login")
public class AdminLoginApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserLoginApi userLoginApi = new UserLoginApi();
        userLoginApi.doPost(req, resp);
    }
}
