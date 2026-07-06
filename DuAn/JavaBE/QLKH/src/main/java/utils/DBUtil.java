package utils;

import java.sql.*;

public class DBUtil {
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/QLKH";
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            return conn;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void closeConnection(Connection conn, ResultSet rs , PreparedStatement pstmt) {
        if (rs != null) {
            try {
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
