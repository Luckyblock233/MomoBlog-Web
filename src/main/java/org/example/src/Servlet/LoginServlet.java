package org.example.src.Servlet;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.example.src.Dao.SessionDao;
import org.example.src.Dao.UserDao;
import org.example.src.entity.User;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();

        String username = request.getParameter("nowUsername");
        String password = request.getParameter("nowPassword");
        String code = request.getParameter("nowCaptchaCode");

        HttpSession httpSession = request.getSession(true);
        httpSession.setMaxInactiveInterval(60); //防止时间过长
        String captchaCode = (String) httpSession.getAttribute("captchaCode");

        if (captchaCode == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error="
                    + java.net.URLEncoder.encode("验证码为空", StandardCharsets.UTF_8));
            return;
        }
        if (!code.equals(captchaCode)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error="
                    + java.net.URLEncoder.encode("验证码错误", StandardCharsets.UTF_8));
            return;
        }

        UserDao userDao=new UserDao();
        try {
            if (!userDao.SearchUser(username,password)) {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error="
                        + java.net.URLEncoder.encode("用户名不能为空", StandardCharsets.UTF_8));
            } else {
                Optional<User> user = userDao.findByUsername(username);
                int userId = user.get().getId();

                httpSession = request.getSession(true);
                httpSession.setAttribute("userId", userId);
                httpSession.setAttribute("username", username);
                httpSession.setMaxInactiveInterval(30 * 60);

                if (request.getParameter("keep") != null) {
                    String sessionToken = UUID.randomUUID().toString();
                    SessionDao sessionDao=new SessionDao();
                    sessionDao.insertSession(user.get().getId(), sessionToken);

                    Cookie cookie = new Cookie("sessionToken", sessionToken);
                    cookie.setMaxAge(2 * 7 * 24 * 60 * 60);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }

                response.sendRedirect("protected/home.html");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        this.doGet(request,response);
    }
}