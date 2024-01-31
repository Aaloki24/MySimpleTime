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

public class EmployeeService {
    private final List<Employee> employees;
    private boolean isClockedIn = false;

    public EmployeeService(List<Employee> employees) {
        this.employees = employees;
    }

    public void employeeMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. Clock In/Out");
            System.out.println("2. View Work Hours");
            System.out.println("0. Log Out");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    clockInOut();
                    break;
                case 2:
                    System.out.print("Enter the employee ID to view work hours: ");
                    int employeeId = scanner.nextInt();
                    scanner.nextLine(); 
                    viewWorkHours(employeeId); 
                    break;
                case 0:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void clockInOut() {
        if (isClockedIn) {
            System.out.println("You have clocked out.");
        } else {
            System.out.println("You have clocked in.");
        }
        isClockedIn = !isClockedIn; 
    }

    public void viewWorkHours(int employeeId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT workHours FROM employees WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Work Hours: " + rs.getDouble("workHours"));
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving work hours.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}




