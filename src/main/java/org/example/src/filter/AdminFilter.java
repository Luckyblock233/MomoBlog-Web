package org.example.src.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdminFilter  implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = ((HttpServletRequest) req).getServletPath();

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null && httpSession.getAttribute("username") != null) {
            if ("/protected/home.html".equals(path) && "root".equals(httpSession.getAttribute("username"))) {
                response.sendRedirect(request.getContextPath() + "/protected/admin.html");
            }
            if ("/protected/admin.html".equals(path) && !"root".equals(httpSession.getAttribute("username"))) {
                response.sendRedirect(request.getContextPath() + "/protected/home.html");
            }
        }
        chain.doFilter(req, res);
    }
}
