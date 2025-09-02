package Auth;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class StudentLogin {

    public static boolean login(Scanner sc) {
        System.out.print("Enter Roll No: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\\\Users\\\\DELL\\\\eclipse-workspace\\\\StudentManagmentSystem\\\\src\\\\Auth\\\\students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(rollNo) && data[2].equals(password)) {
                    System.out.println("Student Login Successful! Welcome " + data[1]);
                    markAttendance(rollNo);
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (!found) {
            System.out.println("Invalid Roll No or Password");
        }

        return found;
    }
    

    private static void markAttendance(String rollNo) {
    	String today = LocalDate.now().toString();
        boolean alreadyMarked = false;

        // Step 1: Check if already marked for today
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\assignment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fileRoll = parts[0];
                    String fileDate = parts[1];
                    if (fileRoll.equals(rollNo) && fileDate.equals(today)) {
                        alreadyMarked = true;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // file not yet created → ok
        } catch (IOException e) {
            System.out.println("Error reading attendance: " + e.getMessage());
        }

        // Step 2: If not already marked, write new entry
        if (!alreadyMarked) {
            try (FileWriter fw = new FileWriter("aC:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\assignment.txt", true)) {
                fw.write(rollNo + "," + today + ",Present\n");
                System.out.println("Attendance marked for roll " + rollNo + " on " + today);
            } catch (IOException e) {
                System.out.println("Error writing attendance: " + e.getMessage());
            }
        } else {
            System.out.println("Attendance already marked for roll " + rollNo + " today.");
        }
    }
    
    public static void viewAttendance(Scanner sc) {
        System.out.print("Enter Roll No to view (or press Enter for all): ");
        String input = sc.nextLine().trim();

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            boolean found = false;

            System.out.println("\n--- Attendance Records ---");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String rollNo = parts[0];
                    String date = parts[1];
                    String status = parts[2];

                    // Show all or only the requested roll no
                    if (input.isEmpty() || rollNo.equals(input)) {
                        System.out.println("Roll: " + rollNo + " | Date: " + date + " | Status: " + status);
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No attendance records found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance: " + e.getMessage());
        }
    }

}

