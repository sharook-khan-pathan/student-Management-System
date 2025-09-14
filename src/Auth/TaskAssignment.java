package Auth;

import java.io.*;
import java.util.*;
import java.sql.*;

public class TaskAssignment {
	
	private static String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\assignment.txt";
	
	//Admin Task Assignment
	public static void addAssignment(Scanner sc) {
		
		try {
	        System.out.print("Enter Task ID: ");
	        String taskId = sc.nextLine().trim(); // TASKID is VARCHAR2(5)

	        System.out.print("Enter Roll No of Student: ");
	        int rollNo = Integer.parseInt(sc.nextLine().trim()); // ROLLNO is NUMBER

	        System.out.print("Enter Task Description: ");
	        String desc = sc.nextLine();

	        String status = "Pending"; // default status

	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        try (Connection conn = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

	            // Step 1: Check if student exists
	            String checkSql = "SELECT 1 FROM student WHERE rollno = ?";
	            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
	                checkPs.setInt(1, rollNo);
	                ResultSet rs = checkPs.executeQuery();

	                if (!rs.next()) {
	                    System.out.println("❌ Roll No " + rollNo + " does not exist in students table!");
	                    return; // stop here
	                }
	            }

	            // Step 2: Assign task
	            String insertSql = "INSERT INTO task (taskid, rollno, description, status) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
	                ps.setString(1, taskId);   // TASKID is VARCHAR2(5)
	                ps.setInt(2, rollNo);      // ROLLNO is NUMBER
	                ps.setString(3, desc);     // DESCRIPTION is VARCHAR2(10)
	                ps.setString(4, status);   // STATUS is VARCHAR2(10)

	                int rows = ps.executeUpdate();
	                if (rows > 0) {
	                    System.out.println("✅ Task Assigned Successfully to Roll No: " + rollNo);
	                } else {
	                    System.out.println("❌ Failed to assign task!");
	                }
	            }
	        }

	    } 
		catch (NumberFormatException e) {
	        System.out.println("⚠ Roll No must be a number!");
	    } 
		catch (Exception e) {
	        System.out.println("⚠ Error assigning task: " + e.getMessage());
	        e.printStackTrace();
	    }
		
	}
	
	// Admin View All Tasks
//	public static void viewAllTasks() {
//	    try {
//	        Class.forName("oracle.jdbc.driver.OracleDriver");
//	        try (Connection conn = DriverManager.getConnection(
//	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
//	             PreparedStatement ps = conn.prepareStatement(
//	                    "SELECT taskid, rollno, description, status FROM task");
//	             ResultSet rs = ps.executeQuery()) {
//
//	            System.out.println("\n--- 📋 All Assigned Tasks ---");
//	            boolean found = false;
//	            while (rs.next()) {
//	                found = true;
//	                System.out.println("Task ID    : " + rs.getString("taskid"));
//	                System.out.println("Roll No    : " + rs.getInt("rollno"));
//	                System.out.println("Description: " + rs.getString("description"));
//	                System.out.println("Status     : " + rs.getString("status"));
//	                System.out.println("---------------------------");
//	            }
//
//	            if (!found) {
//	                System.out.println("⚠ No tasks assigned yet.");
//	            }
//
//	        }
//	    } catch (Exception e) {
//	        System.out.println("⚠ Error fetching tasks: " + e.getMessage());
//	        e.printStackTrace();
//	    }
//	}

	//Student View Task
	public static void viewTasks(Scanner sc) {
		
		System.out.print("Enter Student Roll No: ");
	    int rollNo = Integer.parseInt(sc.nextLine().trim());

	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        try (Connection conn = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
	             PreparedStatement ps = conn.prepareStatement(
	                    "SELECT taskid, description, status FROM task WHERE rollno = ?")) {

	            ps.setInt(1, rollNo);
	            ResultSet rs = ps.executeQuery();

	            System.out.println("\n--- 📋 Tasks for Student Roll No: " + rollNo + " ---");
	            boolean found = false;
	            while (rs.next()) {
	                found = true;
	                System.out.println("Task ID    : " + rs.getString("taskid"));
	                System.out.println("Description: " + rs.getString("description"));
	                System.out.println("Status     : " + rs.getString("status"));
	                System.out.println("---------------------------");
	            }

	            if (!found) {
	                System.out.println("⚠ No tasks found for this student.");
	            }

	        }
	    } catch (Exception e) {
	        System.out.println("⚠ Error fetching tasks: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	//Update Task Status
	
	// 🔹 Update Task Status in DB
	public static void updateTaskStatus(Scanner sc) {
	    System.out.print("Enter Roll No: ");
	    int rollNo = Integer.parseInt(sc.nextLine().trim());

	    System.out.print("Enter Task ID to update: ");
	    String taskId = sc.nextLine().trim();

	    System.out.print("Enter New Status (Pending/In Progress/Completed): ");
	    String newStatus = sc.nextLine().trim();

	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        try (Connection conn = DriverManager.getConnection(
	                     "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
	             PreparedStatement ps = conn.prepareStatement(
	                     "UPDATE task SET status = ? WHERE taskid = ? AND rollno = ?")) {

	            ps.setString(1, newStatus);
	            ps.setString(2, taskId);
	            ps.setInt(3, rollNo);

	            int rowsUpdated = ps.executeUpdate();

	            if (rowsUpdated > 0) {
	                System.out.println("✅ Task status updated successfully!");
	            } else {
	                System.out.println("❌ Task not found for your Roll No.");
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("⚠ Error while updating task: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}

	

