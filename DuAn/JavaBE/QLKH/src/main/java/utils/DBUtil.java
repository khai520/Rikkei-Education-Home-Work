package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
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
}
