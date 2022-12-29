package com.example.book.controller.book.front;

import com.example.book.controller.book.backend.BookListApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/front/book-list-api")
public class BookListFrontApi extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BookListApi bookListApi = new BookListApi();
        bookListApi.doGet(req, resp);
    }
}
