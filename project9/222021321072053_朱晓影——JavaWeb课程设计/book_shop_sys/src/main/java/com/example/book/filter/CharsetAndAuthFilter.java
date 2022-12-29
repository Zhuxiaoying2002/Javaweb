package com.example.book.filter;

import com.example.book.pojo.User;
import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

@WebFilter("/*")
public class CharsetAndAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        System.out.println("CharsetAndAuthFilter init()");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 请求编码设置成utf8
        request.setCharacterEncoding("utf8");
        // 只拦截user|admin资源
        String uri = request.getRequestURI();
        System.out.println("url ==> " + uri);

        // token获取
        String token = request.getParameter("token");
        System.out.println("token: " + token);

        if (uri.contains("/user") || uri.contains("/admin")) {

            if (token == null || "".equals(token)) {
                response.setContentType("application/json; charset=UTF-8");
                try (Writer writer = response.getWriter()) {
                    ApiSerialize.dumpsToJson(writer, CommonResponse.forbidden());
                }
                return;
            }
            if (uri.contains("/user")) {
                response.setContentType("application/json; charset=UTF-8");
                if (!token.equals("0")) {
                    try (Writer writer = response.getWriter()) {
                        ApiSerialize.dumpsToJson(writer, CommonResponse.forbidden());
                    }
                    return;
                }
            }
            if (uri.contains("/admin")) {
                if (!token.equals("1")) {
                    try (Writer writer = response.getWriter()) {
                        ApiSerialize.dumpsToJson(writer, CommonResponse.forbidden());
                    }
                    return;
                }
            }
        }

        // 放行 然后执行后续过滤器
        filterChain.doFilter(request, response);
    }
}
