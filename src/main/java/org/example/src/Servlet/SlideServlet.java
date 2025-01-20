package org.example.src.Servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.src.Dao.SlideDao;
import org.example.src.entity.Slide;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class SlideServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        SlideDao slideDao =new SlideDao();
        try {
            List<Slide> slideList = slideDao.fetchSlide();
            out.write(gson.toJson(slideList));
        } catch (SQLException e) {
            e.printStackTrace();
            out.write(gson.toJson(new ResponseMessage(false, e.getMessage())));
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getPathInfo().substring(1));

        SlideDao slideDao =new SlideDao();
        try {
            if (slideDao.searchSlide(id)) {
                out.write(gson.toJson(new ResponseMessage(false, "博文推荐已添加")));
            } else {
                slideDao.insertSlide(id);
                out.write(gson.toJson(new ResponseMessage(true, "博文推荐添加成功")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.write(gson.toJson(new ResponseMessage(false, e.getMessage())));
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getPathInfo().substring(1));
        SlideDao slideDao = new SlideDao();

        try {
            int rowsAffected = slideDao.deleteByBlogId(id);
            if (rowsAffected > 0) {
                out.write(gson.toJson(new ResponseMessage(true, "博文推荐删除成功")));
            } else {
                out.write(gson.toJson(new ResponseMessage(false, "博文推荐未找到")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.write(gson.toJson(new ResponseMessage(false, e.getMessage())));
        }
    }

    private class ResponseMessage {
        public boolean success;
        public String message;

        public ResponseMessage(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}