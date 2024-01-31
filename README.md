SimpleTime README

Overview:
SimpleTime is a console-based Java application designed to manage employee time tracking and payroll calculations for small to medium-sized businesses.

Installation
To run this application, you will need:
- Java Development Kit (JDK) installed on your machine. You can download it from Oracle's official site.
- MySQL database server running locally on your machine. Download it from MySQL's official site.
- An Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or NetBeans.

Setup
- Clone the repository to your local machine or download the zip and extract it.
- Open the project in your IDE.
- Make sure the MySQL JDBC driver is included in the project's library path.
- Create a database in your MySQL server and adjust the database connection URL, username, and password in DBConnector.java accordingly.

Database Configuration
Run the following SQL scripts to create the necessary tables:

-- Create users table
CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL, 
  `role` ENUM('admin', 'employee') NOT NULL,
  PRIMARY KEY (`user_id`)
);

-- Create employees table
CREATE TABLE `employees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `age` INT NULL,
  `designation` VARCHAR(255) NULL,
  `department` VARCHAR(255) NULL,
  `workHours` DECIMAL(10,2) NULL,
  `hourlyRate` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id`)
);


Running the Application
- Compile the Java files in the src folder.
- Run Main.java.
- The console will prompt you with options to log in, register, or exit.
- Follow the on-screen instructions to interact with the program.

Features
- Login/Registration System: Users can register as an employee or admin and log in to access different functionalities.
- Employee Management: Admins can add, edit, or remove employees and their details.
- Time Tracking: Employees can clock in and out, and admins can view a summary of work hours.
- Payroll Calculation: Admins can calculate payroll based on work hours and hourly rates.

Troubleshooting
If you encounter any connection issues, ensure that your MySQL server is running and that the credentials in DBConnector.java are correct.
Make sure that the database schema and tables are set up correctly 
