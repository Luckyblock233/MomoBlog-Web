package org.example.src.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.example.src.Dao.UserDao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("nowUsername");
        String password = request.getParameter("nowPassword");
        String code = request.getParameter("nowCaptchaCode");

        HttpSession httpSession = request.getSession(true);
        httpSession.setMaxInactiveInterval(60);
        String captchaCode = (String) httpSession.getAttribute("captchaCode");

        if (captchaCode == null) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error="
                    + java.net.URLEncoder.encode("验证码失效", StandardCharsets.UTF_8));
            return;
        }
        if (!code.equals(captchaCode)) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error="
                    + java.net.URLEncoder.encode("验证码错误", StandardCharsets.UTF_8));
            return;
        }

        UserDao userDao=new UserDao();
        try {
            if (userDao.findByUsername(username).isPresent()) {
                response.sendRedirect(request.getContextPath() + "/register.jsp?error="
                        + java.net.URLEncoder.encode("用户名已存在", StandardCharsets.UTF_8));
            } else {
                userDao.insertUser(username, password);
                response.sendRedirect(request.getContextPath() + "/index.jsp?error="
                        + java.net.URLEncoder.encode("注册成功", StandardCharsets.UTF_8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        this.doGet(request,response);
    }
}