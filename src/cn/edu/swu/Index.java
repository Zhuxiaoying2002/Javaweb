package cn.edu.swu;

import cn.edu.swu.book.Book;
import cn.edu.swu.book.BookRepo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/index.html")
public class Index extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Book> books = null;
        try {
            books = BookRepo.getInstance().getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<br><div class='book-group'>\n");
        for (Book book : books) {
            sb.append("<div class='book-div'>");
            sb.append("<div class='book-pic'><img src=\"./images/b11.jpg\"/></div>");
            sb.append("<div class='book-name'>" + book.getName() + "</div>");
            sb.append("<div class='book-author'>作者: " + book.getAuthor() + "</div>");
            sb.append("<div class='book-price'>价格：" + book.getPrice()+ "</div>");
            sb.append("</div>\n");
        }
        sb.append("</div><br>\n");

        String page = "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>My Java Web APP</title>\n" +
                "        <link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">" +
                "    </head>\n" +
                "    <body>\n" +
                "        <center style=\"margin-top:1em\">\n" +
                "            <h1>欢迎访问我的网上书店</h1>\n" +
                "            <div style=\"margin-top:2em; width: 50%\">\n" +
//                "                <div>\n" +
//                "                    <div style=\"float:left;padding-right:3em\"><a href=\"./listBook\">查看图书</a></div>\n" +
//                "                </div>\n" +
                "                <div>\n" +
                "                    <div style=\"float:left;padding-right:3em\"><a href=\"./login\">登录后台</a></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <br><hr width='80%'>\n" +
                "          "  + sb.toString() +
                "        </center>\n" +
                "    </body>\n" +
                "</html>";

        response.setContentType("text/html; Charset=utf8");
        try(Writer writer = response.getWriter()) {
            writer.write(page);
        }
    }

    @WebFilter(filterName = "CharsetFilter")
    public static class LoginFilter implements Filter {
        public void destroy() {
            /*销毁时调用*/
        }

        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
            /*过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理*/

            chain.doFilter(req, resp);//交给下一个过滤器或servlet处理
        }

        public void init(FilterConfig config) throws ServletException {

            /*初始化方法  接收一个FilterConfig类型的参数 该参数是对Filter的一些配置*/

        }

    }
}
