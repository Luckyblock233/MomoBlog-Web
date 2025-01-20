package org.example.src.Dao;
import org.example.src.entity.User;
import org.example.src.utils.DBConnection;

import java.sql.*;

public class SessionDao {
    public void insertSession(int userId, String sessionToken) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            preparedStatement = con.prepareStatement("insert into sessiont(userId, sessionToken, expiresAt) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 14 DAY))");
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, sessionToken);
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

    public int getUserIdBySessionToken(String sessionToken) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            preparedStatement = con.prepareStatement("select userId from sessiont where sessionToken = ? and expiresAt > NOW()");
            preparedStatement.setString(1, sessionToken);
            rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt("userId") : 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (preparedStatement != null) preparedStatement.close();
            if (con != null) con.close();
        }
        return 0;
    }
}
