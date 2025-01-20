package org.example.src.Dao;
import org.example.src.entity.Blog;
import org.example.src.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlogDao {
    public void insertBlog(int userId, String title, String content, String imageURL) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            preparedStatement = con.prepareStatement("insert into blogt(userId, postTime, title, content, imageURL) values(?,?,?,?,?)");
            preparedStatement.setInt(1, userId);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, imageURL);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con!=null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public int deleteById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int rs = 0;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "delete from blogt where id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeUpdate();
            return rs;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return 0;
    }

    public Optional<Blog> findById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from blogt left join usert u on blogt.userId = u.id where id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                Blog blog = new Blog();
                blog.setId(rs.getInt("blogt.id"));
                blog.setUserId(rs.getInt("userId"));
                blog.setUsername(rs.getString("username"));
                blog.setPostTime(new Date(rs.getTimestamp("postTime").getTime()));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setImageURL(rs.getString("imageURL"));

                return Optional.of(blog);
            } else {
                return Optional.empty   ();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                rs.close();
            }
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return Optional.empty();
    }

    public List<Blog> fetchBlog(int page, int size) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from blogt left join usert u on blogt.userId = u.id order by -postTime limit ? offset ? ";
            List<Blog> blogList = new ArrayList<>();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, (page - 1) * size);

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Blog blog = new Blog();
                blog.setId(rs.getInt("blogt.id"));
                blog.setUserId(rs.getInt("userId"));
                blog.setUsername(rs.getString("username"));
                blog.setPostTime(new Date(rs.getTimestamp("postTime").getTime()));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setImageURL(rs.getString("imageURL"));

                blogList.add(blog);
            }
            return blogList;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                rs.close();
            }
            if(preparedStatement!=null) {
                preparedStatement.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return null;
    }

    public int countTotal() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();

            String sql = "select count(*) from blogt";
            int total = 0;
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
            return total;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                rs.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return 0;
    }
}