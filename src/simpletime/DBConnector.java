/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connector class for managing connections to the MySQL database.
 *
 * @author aalokipatel
 */
public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/simpletimedb";
    private static final String USER = "root";
    private static final String PASSWORD = "Aaloki24!";
    private static Connection connection = null;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
           
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                
                if (conn == connection) {
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


