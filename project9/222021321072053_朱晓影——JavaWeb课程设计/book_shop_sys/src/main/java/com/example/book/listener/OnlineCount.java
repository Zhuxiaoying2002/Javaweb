package com.example.book.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineCount implements HttpSessionListener {
    /**
     * 功能描述：会话创建时刻进行监听
     *
     * @param se 会话事件
     * @description: 创建一个会话时，在线人数加1
     */
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
        System.out.println("增加一个session");
        if (onlineCount == null) {
            onlineCount = 1;
        } else {
            int count = onlineCount.intValue();
            onlineCount = ++count;
        }
        servletContext.setAttribute("onlineCount", onlineCount);
    }


    /**
     * 功能描述：会话销毁时刻进行监听
     *
     * @param se 会话事件
     * @description: 销毁会话时，在线人数建1
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        System.out.println("减少一个session");
        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
        if (onlineCount == null) {
            onlineCount = 0;
        } else {
            int count = onlineCount.intValue();
            onlineCount = --count;
        }
        servletContext.setAttribute("onlineCount", onlineCount);
    }
}
