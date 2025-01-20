package org.example.src.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.example.src.Dao.SessionDao;
import org.example.src.Dao.UserDao;

import java.io.*;
import java.sql.*;

public class AutoLoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = ((HttpServletRequest) req).getServletPath();

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null && httpSession.getAttribute("userId") != null) {
            if ("/index.jsp".equals(path)) {
                response.sendRedirect(request.getContextPath() + "/protected/home.html");
            } else {
                chain.doFilter(req, res);
            }
            return;
        }

        String sessionToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionToken".equals(cookie.getName())) {
                    sessionToken = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionToken == null) {
            if ("/index.jsp".equals(path)) {
                chain.doFilter(req, res);
            } else {
                response.sendRedirect( request.getContextPath() + "/index.jsp");
            }
            return ;
        }

        try {
            SessionDao sessionDao = new SessionDao();
            int userId = sessionDao.getUserIdBySessionToken(sessionToken);

            if (userId > 0) {
                UserDao userDao = new UserDao();
                httpSession = request.getSession(true);
                httpSession.setAttribute("userId", userId);
                httpSession.setAttribute("username", userDao.findById(userId).get().getUsername());
                httpSession.setMaxInactiveInterval(30 * 60);

                if ("/index.jsp".equals(path)) {
                    response.sendRedirect(request.getContextPath() + "/protected/home.html");
                } else {
                    chain.doFilter(req, res);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
