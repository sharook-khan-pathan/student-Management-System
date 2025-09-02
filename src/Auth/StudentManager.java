package Auth;

import java.io.*;
import java.util.*;

public class StudentManager {

    private static final String STUDENT_FILE = "sC:\\\\Users\\\\DELL\\\\eclipse-workspace\\\\StudentManagmentSystem\\\\src\\\\Auth\\\\students.txt";

    // ---- Update Student Profile (phone, email) ----
    public static void updateStudentProfile(String rollNo, Scanner sc) {
        File file = new File(STUDENT_FILE);
        File temp = new File("students_temp.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(rollNo)) {
                    found = true;

                    String name = data[1];       // Name (fixed, cannot change)
                    String password = data[2];   // Password (separate method to change)

                    String phone = (data.length > 3) ? data[3] : "";
                    String email = (data.length > 4) ? data[4] : "";

                    System.out.println("Current Phone: " + (phone.isEmpty() ? "Not set" : phone));
                    System.out.print("Enter New Phone (or press Enter to keep same): ");
                    String newPhone = sc.nextLine().trim();
                    if (newPhone.isEmpty()) newPhone = phone;

                    System.out.println("Current Email: " + (email.isEmpty() ? "Not set" : email));
                    System.out.print("Enter New Email (or press Enter to keep same): ");
                    String newEmail = sc.nextLine().trim();
                    if (newEmail.isEmpty()) newEmail = email;

                    // Rewrite with updated details
                    bw.write(rollNo + "," + name + "," + password + "," + newPhone + "," + newEmail);
                    bw.newLine();

                    System.out.println("Profile updated successfully!");
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }

        if (found) {
            file.delete();
            temp.renameTo(file);
        } else {
            temp.delete();
            System.out.println("Student not found!");
        }
    }

    // ---- Change Password ----
    public static void changePassword(String rollNo, Scanner sc) {
        File file = new File(STUDENT_FILE);
        File temp = new File("students_temp.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(rollNo)) {
                    found = true;

                    String name = data[1];
                    String oldPassword = data[2];
                    String phone = (data.length > 3) ? data[3] : "";
                    String email = (data.length > 4) ? data[4] : "";

                    System.out.print("Enter Old Password: ");
                    String enteredPass = sc.next();

                    if (!enteredPass.equals(oldPassword)) {
                        System.out.println("Wrong password!");
                        bw.write(line);
                        bw.newLine();
                        continue;
                    }

                    System.out.print("Enter New Password: ");
                    String newPass = sc.next();

                    System.out.print("Confirm New Password: ");
                    String confirmPass = sc.next();

                    if (!newPass.equals(confirmPass)) {
                        System.out.println(" Passwords do not match!");
                        bw.write(line);
                        bw.newLine();
                    } else {
                        bw.write(rollNo + "," + name + "," + newPass + "," + phone + "," + email);
                        bw.newLine();
                        System.out.println(" Password changed successfully!");
                    }
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error changing password: " + e.getMessage());
        }

        if (found) {
            file.delete();
            temp.renameTo(file);
        } else {
            temp.delete();
            System.out.println(" Student not found!");
        }
    }
}
