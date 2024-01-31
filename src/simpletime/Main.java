/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simpletime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author aalokipatel
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>(); 
        UserService userService = new UserService();
        
        AdminService adminService = new AdminService(employees);
        EmployeeService employeeService = new EmployeeService(employees);

        boolean running = true; 

        while (running) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1: {
                    User user = userService.login(scanner);
                    if (user != null && "admin".equals(user.getRole())) {
                        adminService.adminMenu();
                    } else if (user != null) {
                        employeeService.employeeMenu();
                    }
                    System.out.println("You have logged out.");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");
                    if (scanner.nextInt() == 0) {
                        System.out.println("Thank you for using SimpleTime, see you again!");
                        running = false; 
                    }
                    scanner.nextLine(); 
                    break;
                }
                case 2: {
                    userService.register(scanner);
                    break;
                }
                case 0: {
                    System.out.println("Thank you for using SimpleTime, see you again!");
                    running = false; 
                    break;
                }
                default: {
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
            }
        }
        scanner.close(); 
    }
}



