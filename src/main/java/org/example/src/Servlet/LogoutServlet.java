package org.example.src.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("sessionToken", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.sendRedirect("index.jsp");
    }
}