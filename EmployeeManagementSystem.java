package com.employeemanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeapplication";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			createEmployeeTable(connection);
			performOperations(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createEmployeeTable(Connection connection) throws SQLException {
		String createTableQuery = "CREATE TABLE IF NOT EXISTS employee (id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "name VARCHAR(100), salary DECIMAL(10, 2))";
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(createTableQuery);
			System.out.println("Employee table created successfully.");
		}
	}

	private static void performOperations(Connection connection) throws SQLException {
		int choice;
		do {
			System.out.println("\nEmployee Management System:");
			System.out.println("1. Select all records");
			System.out.println("2. Update all records");
			System.out.println("3. Add an employee record");
			System.out.println("4. Sort records in ascending order (by salary)");
			System.out.println("5. Sort records in descending order (by salary)");			
			System.out.println("6. Select a specific record by ID");
			System.out.println("7. Delete a specific record by ID");
			System.out.println("8. Exit");
			System.out.print("Enter your choice: ");

			Scanner scanner = new Scanner(System.in);
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				selectAllRecords(connection);
				break;
			case 2:
				updateAllRecords(connection);
				break;
			
			case 4:
				sortRecordsBySalary(connection);
				break;
			case 5:
				sortRecordsBySalaryDes(connection);
				break;
			case 3:
				addEmployeeRecord(connection);
				break;
			case 6:
				selectRecordById(connection);
				break;
			case 7:
				deleteRecordById(connection);
				break;
			case 8:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice! Please try again.");
				break;
			}
		} while (choice != 8);
	}

	private static void deleteRecordById(Connection connection) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id:");
		int idToDelete = sc.nextInt();

		String deleteQuery = "DELETE FROM employee WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setInt(1, idToDelete);
			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println(rowsAffected + " record(s) deleted.");
		}
	}

	private static void selectAllRecords(Connection connection) throws SQLException {
		System.out.println("All records in the employee table");
		String selectQuery = "SELECT * FROM employee";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");

				System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
			}
		}
	}

	private static void updateAllRecords(Connection connection) throws SQLException {
    	    int choice;
    	    do {
    	    	
    	                System.out.println("Update all records:");
    	                System.out.println("1. Update by ID");
    	                System.out.println("2. Update by Name");
    	                System.out.println("3. Update by Salary");
    	                System.out.println("4. exit");
    	                System.out.println("Enter your choice: ");
    	    	        Scanner scanner = new Scanner(System.in);
    	    	        choice = scanner.nextInt();  
    	                switch (choice) {
    	                    case 1: // Update by ID
    	                        updateRecordsById(connection);
    	                        break;
    	                    case 2: // Update by Name
    	                        updateRecordsByName(connection);
    	                        break;
    	                    case 3: // Update by Salary
    	                        updateRecordsBySalary(connection);
    	                        break;
    	                    default:
    	                        System.out.println("Invalid choice! Please try again.");
    	                        break;
    	                }
    	    }while(choice!=4);

	}

	private static void updateRecordsById(Connection connection) throws SQLException {
    	    Scanner scanner = new Scanner(System.in);
    	 
    	    	 System.out.println("Enter the employee ID to update: ");
    	    	    int id = scanner.nextInt();
    	    	    System.out.println("Which column you update 1.name 2.salary 3.exit");
    	    	    int choice=scanner.nextInt();  	    
    	    	    switch(choice)
    	    	    {
    	    	    case 1:
    	    	    	System.out.println("Enter the new name: ");
    	        	    String name = scanner.next();

    	        	    String updateQuery = "UPDATE employee SET name = ? WHERE id = ?";
    	        	    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
    	        	        preparedStatement.setString(1, name);
    	        	        preparedStatement.setInt(2, id);

    	        	        int rowsAffected = preparedStatement.executeUpdate();
    	        	        System.out.println(rowsAffected + " record(s) updated.");
    	    	    }
    	        	    break;
    	    	    case 2:
    	    	    System.out.println("Enter the new salary: ");
    	    	    int salary = scanner.nextInt();

    	    	    String updateQuery1 = "UPDATE employee SET salary = ? WHERE id = ?";
    	    	    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery1)) {
    	    	        preparedStatement.setInt(1, salary);
    	    	        preparedStatement.setInt(2, id);

    	    	        int rowsAffected = preparedStatement.executeUpdate();
    	    	        System.out.println(rowsAffected + " record(s) updated.");
    	    	    }
    	    	    break;
    	    	    default:
    	    	    	System.out.println("Invalid choice! Please try again.");
    	    	    }
    	      	   
	}

	private static void updateRecordsByName(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the employee name to update: ");
		String name = scanner.next();
		System.out.println("Enter the new salary: ");
		double salary = scanner.nextDouble();

		String updateQuery = "UPDATE employee SET salary = ? WHERE name = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setDouble(1, salary);
			preparedStatement.setString(2, name);

			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println(rowsAffected + " record(s) updated.");
		}
	}

	private static void updateRecordsBySalary(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the employee salary to update: ");
		double salary = scanner.nextDouble();
		System.out.println("Enter the new ID: ");
		int id = scanner.nextInt();

		String updateQuery = "UPDATE employee SET id = ? WHERE salary = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.setDouble(2, salary);

			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println(rowsAffected + " record(s) updated.");
		}
	}

	private static void deleteRecordsWithSalary(Connection connection, double salaryThreshold) throws SQLException {
		String deleteQuery = "DELETE FROM employee WHERE salary >= ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
			preparedStatement.setDouble(1, salaryThreshold);
			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println(rowsAffected + " record(s) deleted.");
		}
	}

	private static void sortRecordsBySalary(Connection connection) throws SQLException {
		System.out.println("Select attribute\n 1.id 2.name 3.salary 4.exit");
		
		Scanner scanner=new Scanner(System.in);
		int choice=scanner.nextInt();
		switch(choice)
		{
		case 1:
		    sortRecordsByAttribute(connection, "id");
		    break;
		case 2:
		    sortRecordsByAttribute(connection, "name");
		    break;
		case 3:
		    sortRecordsByAttribute(connection, "salary");
		    break;
			
		default:
			System.out.println("Invalid choice! Please try again.");
		}
				
		}
		
	private static void sortRecordsByAttribute(Connection connection, String attribute) throws SQLException {
		System.out.println("Sort  all record using salary ascending  by " + attribute);
		 String selectQuery = "SELECT * FROM employee ORDER BY " + attribute + " ASC";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");

				System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
			}
		}
	}

	private static void sortRecordsBySalaryDes(Connection connection) throws SQLException {
		System.out.println("Select attribute\n 1.id 2.name 3.salary 4.exit");
		
		Scanner scanner=new Scanner(System.in);
		int choice=scanner.nextInt();
		switch(choice)
		{
		case 1:
		    sortRecordsByAttribute1(connection, "id");
		    break;
		case 2:
		    sortRecordsByAttribute1(connection, "name");
		    break;
		case 3:
		    sortRecordsByAttribute1(connection, "salary");
		    break;
			
		default:
			System.out.println("Invalid choice! Please try again.");
		}
				
	}
	private static void sortRecordsByAttribute1(Connection connection,  String attribute) throws SQLException {
		System.out.println("Sort  all record using salary ascending  by " + attribute);
		 String selectQuery = "SELECT * FROM employee ORDER BY " + attribute + " DESC";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");

				System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
			}
		}
	}

	private static void addEmployeeRecord(Connection connection) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the employee Id");
		int id = sc.nextInt();
		System.out.println("Enter the employee name");
		String name = sc.next();
		System.out.println("Enter the employee salary");
		double salary = sc.nextDouble();

		String insertQuery = "INSERT INTO employee (id, name, salary) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, salary);

			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println(rowsAffected + " record(s) inserted.");
		}
	}

	private static void selectRecordById(Connection connection) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the employee Id");

		int id = sc.nextInt();

		String selectQuery = "SELECT * FROM employee WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String name = resultSet.getString("name");
					double salary = resultSet.getDouble("salary");

					System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
				}
			}
		}
	}
}
