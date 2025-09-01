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
                    System.out.println("✅ Student Login Successful! Welcome " + data[1]);
                    markAttendance(rollNo);
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (!found) {
            System.out.println("❌ Invalid Roll No or Password");
        }

        return found;
    }

    private static void markAttendance(String rollNo) {
        try (FileWriter fw = new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\attendence.txt", true)) {
            fw.write(rollNo + "," + LocalDate.now() + ",Present\n");
        } catch (IOException e) {
            System.out.println("Error marking attendance: " + e.getMessage());
        }
    }
}

