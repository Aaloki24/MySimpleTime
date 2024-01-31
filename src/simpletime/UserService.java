/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletime;
import java.util.Scanner;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aalokipatel
 */
public class UserService {
    
    public User login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                return new User(rs.getString("username"), rs.getString("password"), rs.getString("role"));
            } else {
                System.out.println("User not found. Would you like to register?");
                System.out.println("1. Register");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 1) {
                    register(scanner);
                } else {
                    System.out.println("Exiting. Please try again later.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Login failed due to an error. Please try again later.");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("An error occurred while closing the database connection. Please try again later.");
                e.printStackTrace();
            }
        }
        return null;
    }

    public void register(Scanner scanner) {
    System.out.print("Choose a username: ");
    String username = scanner.nextLine();
    System.out.print("Choose a password: ");
    String password = scanner.nextLine(); 
    System.out.print("Choose a role (admin/employee): ");
    String role = scanner.nextLine();

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnector.getConnection();
        String checkSql = "SELECT count(*) FROM users WHERE username = ?";
        stmt = conn.prepareStatement(checkSql);
        stmt.setString(1, username);
        rs = stmt.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            System.out.println("Username already exists. Please login.");
        } else {
            if (stmt != null) stmt.close();

            String insertSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(insertSql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registration successful!");
                System.out.println("Thank you for registering. You can now login using your credentials.");
                System.exit(0);
            } else {
                System.out.println("Registration failed.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error during registration.");
    } finally {
        closeResources(rs, stmt, conn);
    }
}

    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while closing the database resources.");
            e.printStackTrace();
        }
    }
   
}