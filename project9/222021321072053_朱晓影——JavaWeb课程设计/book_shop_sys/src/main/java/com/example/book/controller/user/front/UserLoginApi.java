package com.example.book.controller.user.front;

import com.example.book.pojo.User;
import com.example.book.service.UserServiceImpl;
import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;
import com.mysql.cj.BindValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/front-login")
public class UserLoginApi extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 参数获取
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String validateCode = req.getParameter("validateCode");


        // 设置响应类型
        resp.setContentType("application/json; charset=UTF-8");

        // 参数完整性校验
        if (username == null || "".equals(username) || password == null || password.equals("") || validateCode == null || validateCode.equals("")) {
            // 参数非空校验失败
            CommonResponse response = CommonResponse.fail(500, "参数不完整");

            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer, response);
            }
            return;
        }

        // 参数合法性校验
        User user = null;
        UserServiceImpl userService = new UserServiceImpl();
        try {
             user = userService.authUser(username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(500, "服务器错误"));
            }
            return;
        }
        if (user == null) {
            try (Writer writer = resp.getWriter();) {
                ApiSerialize.dumpsToJson(writer,
                        CommonResponse.fail(500, "用户名或密码错误"));
            }
            return;
        }

        // 写入登录人数
        Object onlineObj = req.getServletContext().getAttribute("online");
        if (onlineObj == null) {
            req.getServletContext().setAttribute("online", 1);
        }
        else {
            req.getServletContext().setAttribute("online", (int) onlineObj + 1);
        }

        // 登录成功 返回登录对象
        try (Writer writer = resp.getWriter();) {
            ApiSerialize.dumpsToJson(writer,
                    CommonResponse.success("登录成功", user));
        }
    }
}
