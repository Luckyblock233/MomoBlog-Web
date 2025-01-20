package org.example.src.Dao;

import org.example.src.entity.Slide;
import org.example.src.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlideDao {
    public boolean searchSlide(int blogId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from slidet where blogId=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, blogId);
            rs = preparedStatement.executeQuery();
            return rs.next();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return false;
    }

    public void insertSlide(int blogId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            preparedStatement = con.prepareStatement("insert into slidet(blogId) values(?)");
            preparedStatement.setInt(1, blogId);
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

    public int deleteByBlogId(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int rs = 0;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "delete from slidet where blogId=?";
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

    public Optional<Slide> findById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from slidet" +
                    "    left join blogt b on blogId = b.id" +
                    "    left join usert u on b.userId = u.id" +
                    "where blogId=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                Slide slide = new Slide();
                slide.setId(rs.getInt("id"));
                slide.setBlogId(rs.getInt("blogId"));
                slide.setUsername(rs.getString("username"));
                slide.setTitle(rs.getString("title"));
                slide.setImageURL(rs.getString("imageURL"));

                return Optional.of(slide);
            } else {
                return Optional.empty();
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

    public List<Slide> fetchSlide() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from slidet" +
                    "    left join blogt b on blogId = b.id" +
                    "    left join usert u on b.userId = u.id";
            List<Slide> slideList = new ArrayList<>();
            preparedStatement = con.prepareStatement(sql);

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Slide slide = new Slide();
                slide.setId(rs.getInt("id"));
                slide.setBlogId(rs.getInt("blogId"));
                slide.setUsername(rs.getString("username"));
                slide.setTitle(rs.getString("title"));
                slide.setImageURL(rs.getString("imageURL"));

                slideList.add(slide);
            }
            return slideList;

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
}