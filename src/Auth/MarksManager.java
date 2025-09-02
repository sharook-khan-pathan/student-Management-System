package Auth;
import java.util.*;
import java.io.*;
public class MarksManager {
	private static final String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\marks.txt";

    // 🔹 Enter marks for a student
    public static void enterMarks(Scanner sc) {
        System.out.print("Enter Roll No: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();

        System.out.print("Enter Marks (0-100): ");
        int marks = sc.nextInt();
        sc.nextLine();

        String grade = calculateGrade(marks);

        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(rollNo + "," + subject + "," + marks + "," + grade + "\n");
            System.out.println("Marks Added Successfully! Grade: " + grade);
        } catch (IOException e) {
            System.out.println("Error writing marks: " + e.getMessage());
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
    public static void viewMarksheet(String rollNo) {
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            System.out.println("\n--- Marksheet for Roll No: " + rollNo + " ---");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(rollNo)) {
                    System.out.println("Subject: " + data[1] + " | Marks: " + data[2] + " | Grade: " + data[3]);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading marks: " + e.getMessage());
        }

        if (!found) {
            System.out.println("No marks found for this student.");
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
