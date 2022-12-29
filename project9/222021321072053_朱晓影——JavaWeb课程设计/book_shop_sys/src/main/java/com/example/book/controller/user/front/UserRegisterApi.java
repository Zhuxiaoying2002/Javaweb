package com.example.book.controller.user.front;

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

@WebServlet("/register")
public class UserRegisterApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String validateCode = req.getParameter("validateCode");

        // 设置响应类型
        resp.setContentType("application/json; charset=UTF-8");

        if (username == null || username.equals("")
                || password == null || password.equals("")
                || validateCode == null || validateCode.equals("")) {
            // 参数非空校验失败
            CommonResponse response = CommonResponse.fail(500, "参数不完整");

            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer, response);
            }
            return;
        }

        // 参数校验检查是否注册
        UserServiceImpl userService = new UserServiceImpl();
        User user = null;
        try {
            user = userService.getUserByUsername(username);
        }
        catch (SQLException e) {
            e.printStackTrace();
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(500, "服务器错误"));
            }
            return;
        }
        if (user != null) {
            // 用户已经注册
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(500, "用户已经注册"));
            }
            return;
        }

        // 写入数据库
        try {
            userService.insert(username, password, 0);
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(200, "注册成功"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(500, "注册失败"));
            }
        }
    }
}
