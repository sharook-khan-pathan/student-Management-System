package Auth;
import java.util.*;
import java.io.*;
import java.sql.*;

public class MarksManager {
	private static final String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\marks.txt";

    // 🔹 Enter marks for a student
    public static void enterMarks(Scanner sc) {
    	
    	try {
            System.out.print("Enter Roll No: ");
            int rollNo = Integer.parseInt(sc.nextLine().trim()); // NUMBER

            System.out.print("Enter Subject: ");
            String subject = sc.nextLine().trim(); // VARCHAR2(8)

            System.out.print("Enter Marks (0-100): ");
            int marks = Integer.parseInt(sc.nextLine().trim()); // NUMBER

            // calculate grade
            String grade = calculateGrade(marks);

            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection conn = DriverManager.getConnection(
                         "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234")) {

                // ✅ Step 1: Check if student exists in `students` table
                String checkSql = "SELECT 1 FROM student WHERE rollno = ?";
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setInt(1, rollNo);
                    ResultSet rs = checkPs.executeQuery();

                    if (!rs.next()) {
                        System.out.println("❌ Student with Roll No " + rollNo + " does not exist!");
                        return; // stop execution
                    }
                }

                // ✅ Step 2: Insert marks for that student
                String insertSql = "INSERT INTO marks (rollno, subject, marks, grade) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, rollNo);
                    ps.setString(2, subject);
                    ps.setInt(3, marks);
                    ps.setString(4, grade);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("✅ Marks Added Successfully for Roll No " + rollNo + " | Grade: " + grade);
                    } else {
                        System.out.println("❌ Failed to add marks.");
                    }
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Please enter valid numeric values for Roll No and Marks.");
        } catch (Exception e) {
            System.out.println("⚠ Error entering marks: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // 🔹 View all marks (admin)
    public static void viewAllMarks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            System.out.println("\n--- All Students Marks ---");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Roll: " + data[0] + " | Subject: " + data[1] + " | Marks: " + data[2] + " | Grade: " + data[3]);
            }
        } catch (IOException e) {
            System.out.println("No marks found yet.");
        }
    }

    // 🔹 View marksheet (student)
    public static void viewMarksheet(Scanner sc) {
    	
    	System.out.print("Enter Roll No: ");
        int rollNo = Integer.parseInt(sc.nextLine().trim());

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            try (Connection conn = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "system", "1234");
                 PreparedStatement ps = conn.prepareStatement(
                        "SELECT subject, marks, grade FROM marks WHERE rollno = ?")) {

                ps.setInt(1, rollNo);
                ResultSet rs = ps.executeQuery();

                System.out.println("\n--- 📄 Marksheet for Roll No: " + rollNo + " ---");
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    String subject = rs.getString("subject");
                    int marks = rs.getInt("marks");
                    String grade = rs.getString("grade");

                    System.out.println("Subject: " + subject +
                                       " | Marks: " + marks +
                                       " | Grade: " + grade);
                }

                if (!found) {
                    System.out.println("⚠ No marks found for this student.");
                }

            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Roll No must be numeric!");
        } catch (Exception e) {
            System.out.println("⚠ Error fetching marks: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // 🔹 Grade calculator
    private static String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }
}
