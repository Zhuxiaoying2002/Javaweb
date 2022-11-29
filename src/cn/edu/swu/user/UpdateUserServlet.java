package cn.edu.swu.user;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String password=request.getParameter("password");
        String name=request.getParameter("name");
        String user1=request.getParameter("user");
        User user =new User();
        user.setPassword(password);
        user.setId(id);
        user.setName(name);
        user.setUser(user1);
        boolean success;
        try {
            success= UserRepo.getInstance().update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Users Manager</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <center>\n" +
                "    <div style=\"margin-top:5em; padding: 2em;text-align:center; width:60%; background-color:#EEEEEE\">\n" +
                "      <h2>编辑用户信息</h2>\n" +
                "      <form action=\"./saveBook\" method=\"post\">\n" +
                "        <input type=\"hidden\" name=\"id\" value=\"" + user.getId()+ "\"><br><br>\n" +
                "        账 号： <input type=\"text\" name=\"user\" value=\"" + user.getUser() + "\"><br><br>\n" +
                "        姓 名： <input type=\"text\" name=\"name\" value=\"" + user.getName() + "\"><br><br>\n" +
                "        <input type=\"submit\" value=\" 提 交 信 息\">\n" +
                "      </form>\n" +
                "    </div>\n" +
                "  </center>\n" +
                "</body>\n" +
                "</html>";

        response.setContentType("text/html; Charset=utf8");
        try(Writer writer = response.getWriter()) {
            writer.write(html);
        }
    }

}
