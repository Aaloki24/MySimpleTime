/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletime;
import java.util.List;
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

public class AdminService {
    private List<Employee> employees;
    
    public AdminService(List<Employee> employees) {
        this.employees = employees;
    }

    public void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Work Hours Summary");
            System.out.println("2. Manage Employees");
            System.out.println("3. Calculate Payroll");
            System.out.println("0. Log Out");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewWorkHoursSummary();
                    break;
                case 2:
                    manageEmployees(scanner);
                    break;
                case 3:
                    calculatePayroll();
                    break;
                case 0:
                    running = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void viewWorkHoursSummary() {
    System.out.println("\nWork Hours Summary:");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnector.getConnection();
        String sql = "SELECT name, workHours FROM employees";
        stmt = conn.prepareStatement(sql);

        rs = stmt.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            double workHours = rs.getDouble("workHours");
            System.out.println(name + " worked " + workHours + " hours.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error retrieving work hours summary.");
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


    private void manageEmployees(Scanner scanner) {
        System.out.println("\nEmployee Management:");
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employee");
        System.out.println("3. Remove Employee");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                addEmployee(scanner);
                break;
            case 2:
                editEmployee(scanner);
                break;
            case 3:
                removeEmployee(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void addEmployee(Scanner scanner) {
    System.out.print("Enter ID: ");
    int id = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Age: ");
    int age = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Enter Designation: ");
    String designation = scanner.nextLine();
    System.out.print("Enter Department: ");
    String department = scanner.nextLine();
    System.out.print("Enter Work Hours: ");
    double workHours = scanner.nextDouble();  
    System.out.print("Enter Hourly Rate: ");
    double hourlyRate = scanner.nextDouble();
    scanner.nextLine(); 

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DBConnector.getConnection();
        String sql = "INSERT INTO employees (id, name, age, designation, department, workHours, hourlyRate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setString(4, designation);
        stmt.setString(5, department);
        stmt.setDouble(6, workHours);
        stmt.setDouble(7, hourlyRate);
        
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Failed to add employee.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error adding employee.");
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    private void editEmployee(Scanner scanner) {
    System.out.print("Enter ID of employee to edit: ");
    int id = scanner.nextInt();
    scanner.nextLine(); 
    
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DBConnector.getConnection();
        String sql = "SELECT * FROM employees WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            System.out.print("Enter new Name (" + rs.getString("name") + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new Age (" + rs.getInt("age") + "): ");
            int age = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter new Designation (" + rs.getString("designation") + "): ");
            String designation = scanner.nextLine();
            System.out.print("Enter new Department (" + rs.getString("department") + "): ");
            String department = scanner.nextLine();
            System.out.print("Enter new Work Hours (" + rs.getDouble("workHours") + "): ");
            double workHours = scanner.nextDouble();
            System.out.print("Enter new Hourly Rate (" + rs.getDouble("hourlyRate") + "): ");
            double hourlyRate = scanner.nextDouble();
            scanner.nextLine(); 

            sql = "UPDATE employees SET name = ?, age = ?, designation = ?, department = ?, workHours = ?, hourlyRate = ? WHERE id = ?";
            stmt.close();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, designation);
            stmt.setString(4, department);
            stmt.setDouble(5, workHours);
            stmt.setDouble(6, hourlyRate);
            stmt.setInt(7, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Failed to update employee.");
            }
        } else {
            System.out.println("Employee not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error updating employee.");
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    private void removeEmployee(Scanner scanner) {
    System.out.print("Enter ID of employee to remove: ");
    int id = scanner.nextInt();
    scanner.nextLine(); 

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DBConnector.getConnection();
        String sql = "DELETE FROM employees WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error removing employee.");
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    private void calculatePayroll() {
    System.out.println("\nPayroll Calculation:");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnector.getConnection();
        String sql = "SELECT name, workHours, hourlyRate FROM employees";
        stmt = conn.prepareStatement(sql);

        rs = stmt.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            double workHours = rs.getDouble("workHours");
            double hourlyRate = rs.getDouble("hourlyRate");
            double salary = workHours * hourlyRate;
            System.out.println(name + " earns $" + salary);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error calculating payroll.");
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

