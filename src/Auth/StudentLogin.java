package Auth;


import java.util.Scanner;
import java.sql.*;
public class StudentLogin {

	// ✅ Student login
	public static boolean login(Scanner sc) {
	    System.out.print("Enter Roll No: ");
	    String rollStr = sc.nextLine().trim();

	    System.out.print("Enter Password: ");
	    String password = sc.nextLine().trim();

	    try {
	        int rollNo = Integer.parseInt(rollStr); // ROLLNO is NUMBER

	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        try (Connection conn = DriverManager.getConnection(
	                     "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
	             PreparedStatement ps = conn.prepareStatement(
	                     "SELECT name FROM student WHERE rollno = ? AND password = ?")) {

	            ps.setInt(1, rollNo);
	            ps.setString(2, password);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                String name = rs.getString("name");
	                System.out.println("✅ Student Login Successful! Welcome " + name);

	                // ✅ Call attendance with int
	                markAttendance(rollNo);

	                return true;
	            } else {
	                System.out.println("❌ Invalid Roll No or Password");
	                return false;
	            }
	        }

	    } catch (NumberFormatException e) {
	        System.out.println("⚠ Roll No must be numeric!");
	        return false;
	    } catch (Exception e) {
	        System.out.println("⚠ Error during login: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
    

	// ✅ Attendance function takes int
	// 🔹 Mark Attendance (one entry per student per day)
	private static void markAttendance(int rollNo) {
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        try (Connection conn = DriverManager.getConnection(
	                     "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

	            // Step 1: Check if attendance already exists for today
	            String checkSql = "SELECT 1 FROM attendance " +
	                              "WHERE rollno = ? AND TRUNC(login_date) = TRUNC(SYSDATE)";
	            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
	                checkPs.setInt(1, rollNo);
	                ResultSet rs = checkPs.executeQuery();

	                if (rs.next()) {
	                    System.out.println("ℹ Attendance already marked for Roll No: " + rollNo + " today.");
	                    return; // ✅ skip inserting again
	                }
	            }

	            // Step 2: Insert new record if not already marked
	            String insertSql = "INSERT INTO attendance (rollno, login_date, status) VALUES (?, SYSDATE, 'Present')";
	            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
	                ps.setInt(1, rollNo);
	                ps.executeUpdate();
	                System.out.println("📌 Attendance marked for Roll No: " + rollNo);
	            }

	        }
	    } catch (Exception e) {
	        System.out.println("⚠ Error marking attendance: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
	// 🔹 View Attendance (all or by roll number)
	public static void viewAttendance(Scanner sc) {
	    System.out.print("Enter Roll No to view (or press Enter for all): ");
	    String input = sc.nextLine().trim();

	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        try (Connection conn = DriverManager.getConnection(
	                     "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

	            String sql;
	            PreparedStatement ps;

	            if (input.isEmpty()) {
	                // Show all students' attendance
	                sql = "SELECT a.rollno, s.name, a.login_date, a.status " +
	                      "FROM attendance a " +
	                      "JOIN student s ON a.rollno = s.rollno " +
	                      "ORDER BY a.login_date DESC";
	                ps = conn.prepareStatement(sql);
	            } else {
	                // Show attendance for a particular student
	                sql = "SELECT a.rollno, s.name, a.login_date, a.status " +
	                      "FROM attendance a " +
	                      "JOIN student s ON a.rollno = s.rollno " +
	                      "WHERE a.rollno = ? " +
	                      "ORDER BY a.login_date DESC";
	                ps = conn.prepareStatement(sql);
	                ps.setInt(1, Integer.parseInt(input));
	            }

	            ResultSet rs = ps.executeQuery();

	            System.out.println("\n--- 📋 Attendance Records ---");
	            boolean found = false;

	            while (rs.next()) {
	                found = true;
	                int rollNo = rs.getInt("rollno");
	                String name = rs.getString("name");
	                String date = rs.getDate("login_date").toString();
	                String status = rs.getString("status");

	                System.out.println("Roll: " + rollNo +
	                                   " | Name: " + name +
	                                   " | Date: " + date +
	                                   " | Status: " + status);
	            }

	            if (!found) {
	                System.out.println("⚠ No attendance records found.");
	            }

	            rs.close();
	            ps.close();

	        }
	    } catch (NumberFormatException e) {
	        System.out.println("⚠ Roll No must be numeric if entered.");
	    } catch (Exception e) {
	        System.out.println("⚠ Error reading attendance: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}

