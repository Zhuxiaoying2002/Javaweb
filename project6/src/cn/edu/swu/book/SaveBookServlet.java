package cn.edu.swu.book;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/saveBook")
public class SaveBookServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String describe = request.getParameter("describe");

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(Float.valueOf(price));
        book.setDescribe(describe);
        if (id != null) {
            book.setId(Long.valueOf(id));
        }

        System.out.println(String.format("%s, %s, %s, %s", name, author, price, describe));

        String message = null;
        try {
            BookRepo.getInstance().saveBook(book);
            message = "提交信息保存成功！";
        } catch (SQLException e) {
            e.printStackTrace();
            message = "提交信息保存失败！";
        }

        response.setContentType("text/html; charset=UTF-8");
        try(Writer writer = response.getWriter()) {
            String html = "<center style=‘margin-top:5em’><h1>%s</h1><br><br>" +
                    "<a href='./submit-book.html'>再 次 录 入</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a href='./listBook'>显 示 列 表</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a href='./index.html'>返 回 首 页</a>" +
                    "</center>";
            writer.write(String.format(html, message));
        }
    }

}
