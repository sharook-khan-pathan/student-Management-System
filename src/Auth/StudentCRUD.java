package Auth;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.*;
import java.sql.Statement;

public class StudentCRUD {

	 //private static String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\students.txt";

	    // Add Student
	    public static void addStudent(Scanner sc) throws ClassNotFoundException {
	    	System.out.print("Enter Roll No: ");
	        int rollno = sc.nextInt();   
	        sc.nextLine();
	        System.out.print("Enter Name: ");
	        String name = sc.nextLine();
	        System.out.print("Enter Password: ");
	        String password = sc.nextLine();
	    	try {
	    		Class.forName("oracle.jdbc.driver.OracleDriver");
	    		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
	    		String query = "insert into student(rollno,name,password) values(?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, rollno);
				stmt.setString(2, name);
				stmt.setString(3, password);
	    		stmt.executeUpdate();
	    		System.out.println("Student Details Added Successfully");
	    		conn.close();
	    		stmt.close();
	    	}
	    	
		    catch (SQLException e) {
		            System.out.println("Error adding student: " + e.getMessage());
		        }
	    }

	    // Search Student
	    public static void searchStudent(Scanner sc) throws ClassNotFoundException {
	        System.out.print("Enter Roll No to Search: ");
	        String roll = sc.nextLine().trim();
	        
	        try {
	        	int rollno = Integer.parseInt(roll); // must be a valid number

	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");

	            String sql = "SELECT rollno, name, password FROM student WHERE rollno = ?";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, rollno);   // ✅ matches NUMBER column

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                System.out.println("🎓 Student Found:");
	                System.out.println("Roll No : " + rs.getInt("rollno"));
	                System.out.println("Name    : " + rs.getString("name"));
	                System.out.println("Password: " + rs.getString("password"));
	            } else {
	                System.out.println("❌ Student not found!");
	            }

	            rs.close();
	            ps.close();
	            conn.close();
	        } 
	        catch (NumberFormatException e) {
	            System.out.println("⚠ Roll number must be numeric!");
	        } catch (SQLException e) {
	            System.out.println("⚠ Database Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    // Update Student
	    public static void updateStudent(Scanner sc) {
	    	
	    	System.out.print("Enter Roll No to Update: ");
	        String rollStr = sc.nextLine().trim();

	        try {
	            int rollno = Integer.parseInt(rollStr);

	            // connect to Oracle
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            try (Connection conn = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

	                // First check if student exists
	                String checkSql = "SELECT * FROM student WHERE rollno = ?";
	                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
	                    checkPs.setInt(1, rollno);
	                    ResultSet rs = checkPs.executeQuery();

	                    if (!rs.next()) {
	                        System.out.println("❌ Student Not Found!");
	                        return;
	                    }
	                }

	                // Ask for new values
	                System.out.print("Enter New Name: ");
	                String newName = sc.nextLine();
	                System.out.print("Enter New Password: ");
	                String newPass = sc.nextLine();

	                // Update query
	                String updateSql = "UPDATE student SET name = ?, password = ? WHERE rollno = ?";
	                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
	                    ps.setString(1, newName);
	                    ps.setString(2, newPass);
	                    ps.setInt(3, rollno);

	                    int rows = ps.executeUpdate();
	                    if (rows > 0) {
	                        System.out.println("✅ Student Updated Successfully!");
	                    } else {
	                        System.out.println("❌ Update failed!");
	                    }
	                }

	            }

	        } catch (NumberFormatException e) {
	            System.out.println("⚠ Roll number must be numeric!");
	        } catch (Exception e) {
	            System.out.println("⚠ Error updating student: " + e.getMessage());
	            e.printStackTrace();
	        }
	    	
	    }

	 // Delete Student
	    public static void deleteStudent(Scanner sc) {
	        System.out.print("Enter Roll No to Delete: ");
	        String rollStr = sc.nextLine().trim();

	        try {
	            int rollno = Integer.parseInt(rollStr);

	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            try (Connection conn = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

	                // First check if student exists
	                String checkSql = "SELECT * FROM student WHERE rollno = ?";
	                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
	                    checkPs.setInt(1, rollno);
	                    ResultSet rs = checkPs.executeQuery();

	                    if (!rs.next()) {
	                        System.out.println("❌ Student Not Found!");
	                        return;
	                    }
	                }

	                // Delete student
	                String deleteSql = "DELETE FROM student WHERE rollno = ?";
	                try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
	                    ps.setInt(1, rollno);

	                    int rows = ps.executeUpdate();
	                    if (rows > 0) {
	                        System.out.println("✅ Student Deleted Successfully!");
	                    } else {
	                        System.out.println("❌ Deletion failed!");
	                    }
	                }

	            }

	        } catch (NumberFormatException e) {
	            System.out.println("⚠ Roll number must be numeric!");
	        } catch (Exception e) {
	            System.out.println("⚠ Error deleting student: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
}
