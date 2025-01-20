package org.example.src.Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.src.Dao.BlogDao;
import org.example.src.Dao.SlideDao;
import org.example.src.entity.Blog;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BlogServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        PrintWriter out = response.getWriter();
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        BlogDao blogDao=new BlogDao();
        try {
            List<Blog> blogList = blogDao.fetchBlog(page, size);
            int total = blogDao.countTotal();
            out.write(gson.toJson(new ResponseBlogPage(blogList, total)));

        } catch (SQLException e) {
            e.printStackTrace();
            out.write(gson.toJson(new ResponseMessage(false, e.getMessage())));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        BlogDao blogDao = new BlogDao();

        try {
            String imageURL = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() != 0) {
                String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extractFileExtension(filePart);
                String uploadPath = getServletContext().getRealPath("/uploads/images");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + File.separator + fileName);
                imageURL = "/uploads/images" + File.separator + fileName;
            }

            HttpSession httpSession = request.getSession();
            int userId = (int) httpSession.getAttribute("userId");
            String content = request.getParameter("content");
            String title = request.getParameter("title");
            blogDao.insertBlog(userId, title, content, imageURL);

            out.write(gson.toJson(new ResponseMessage(true, "博客添加成功")));
        } catch (Exception e) {
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
        BlogDao blogDao = new BlogDao();

        try {
            int rowsAffected = blogDao.deleteById(id);
            if (rowsAffected > 0) {

                SlideDao slideDao = new SlideDao();
                if (slideDao.searchSlide(id)) {
                    slideDao.deleteByBlogId(id);
                    out.write(gson.toJson(new ResponseMessage(true, "博文删除成功，顺带删除博文推荐")));
                } else {
                    out.write(gson.toJson(new ResponseMessage(true, "博文删除成功")));
                }

            } else {
                out.write(gson.toJson(new ResponseMessage(false, "博文未找到")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.write(gson.toJson(new ResponseMessage(false, e.getMessage())));
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
    private String extractFileExtension(Part part) {
        String fileName = extractFileName(part);
        if (fileName != null) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                return fileName.substring(dotIndex + 1);
            }
        }
        return null;
    }

    private class ResponseMessage {
        public boolean success;
        public String message;

        public ResponseMessage(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

    private class ResponseBlogPage {
        public List<Blog> blogList;
        public int total;

        public ResponseBlogPage( List<Blog> blogList, int total ) {
            this.blogList = blogList;
            this.total = total;
        }
    }
}
