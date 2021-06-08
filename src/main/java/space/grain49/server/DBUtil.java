package space.grain49.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVE_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/accountbook";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "GYHlgb01234";

    static {
        try {
            Class.forName(DRIVE_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取Connection对象
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /*
     * 释放资源
     */
    public static void close(Connection connection, PreparedStatement stmt, ResultSet rs) {

        try {
            if (null != rs) {
                rs.close();
            }
            if (null != stmt) {
                stmt.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
