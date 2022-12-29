package com.example.book.controller.user.backend;

import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/count")
public class OnlineCountApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Object o = req.getServletContext().getAttribute("online");
        int count = 0;
        if (null == o) {
            count = 1;
        }
        else count = (int) o;
        try (ServletOutputStream writer = resp.getOutputStream()) {
            ApiSerialize.dumpsToJson2(writer, CommonResponse.success(count));
        }
    }
}
