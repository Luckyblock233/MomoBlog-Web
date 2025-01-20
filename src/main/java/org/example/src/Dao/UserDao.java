package org.example.src.Dao;
import org.example.src.entity.User;
import org.example.src.utils.DBConnection;

import java.sql.*;
import java.util.Optional;

public class UserDao {
    public boolean SearchUser(String u,String p) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from usert where username=? and password=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, u);
            preparedStatement.setString(2, p);
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

    public void insertUser(String u, String p) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            preparedStatement = con.prepareStatement("insert into usert(username, password) values(?,?)");
            preparedStatement.setString(1,u);
            preparedStatement.setString(2,p);
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

    public Optional<User> findByUsername(String u) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from usert where username=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, u);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
//                user.setPassword(rs.getString("password"));
                return Optional.of(user);
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
        return null;
    }

    public int deleteByUsername(String u) throws SQLException {
        PreparedStatement preparedStatement = null;
        int rs = 0;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "delete from usert where username=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, u);
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

    public Optional<User> findById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs =null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            String sql = "select * from usert where id=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
//                user.setPassword(rs.getString("password"));
                return Optional.of(user);
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
        return null;
    }
}

