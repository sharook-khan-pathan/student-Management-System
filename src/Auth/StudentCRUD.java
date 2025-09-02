package Auth;

import java.io.*;
import java.util.*;

public class StudentCRUD {

	 private static String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\students.txt";

	    // Add Student
	    public static void addStudent(Scanner sc) {
	        System.out.print("Enter Roll No: ");
	        int roll = sc.nextInt();   
            sc.nextLine();
	        System.out.print("Enter Name: ");
	        String name = sc.nextLine();
	        System.out.print("Enter Password: ");
	        String pass = sc.nextLine();

	        try (FileWriter fw = new FileWriter(FILE, true)) {
	            fw.write(roll + "," + name + "," + pass + "\n");
	            System.out.println("Student Added Successfully!");
	        } catch (IOException e) {
	            System.out.println("Error adding student: " + e.getMessage());
	        }
	    }

	    // Search Student
	    public static void searchStudent(Scanner sc) {
	        System.out.print("Enter Roll No to Search: ");
	        String roll = sc.nextLine();
	        boolean found = false;

	        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                if (data[0].equals(roll)) {
	                    System.out.println("Student Found: Roll: " + data[0] + ", Name: " + data[1]);
	                    found = true;
	                    break;
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error Student Not Found");
	        }
	    }
	    // Update Student
	    public static void updateStudent(Scanner sc) {
	        System.out.print("Enter Roll No to Update: ");
	        String roll = sc.nextLine();
	        List<String> students = new ArrayList<>();
	        boolean updated = false;

	        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                if (data[0].equals(roll)) {
	                    System.out.print("Enter New Name: ");
	                    String newName = sc.nextLine();
	                    System.out.print("Enter New Password: ");
	                    String newPass = sc.nextLine();
	                    students.add(roll + "," + newName + "," + newPass);
	                    updated = true;
	                } else {
	                    students.add(line);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error updating student: " + e.getMessage());
	        }

	        if (updated) {
	            try (FileWriter fw = new FileWriter(FILE)) {
	                for (String s : students) fw.write(s + "\n");
	            } catch (IOException e) {
	                System.out.println("Error writing file: " + e.getMessage());
	            }
	            System.out.println("Student Updated Successfully!");
	        } else {
	            System.out.println("Student Not Found!");
	        }
	    }

	    // Delete Student
	    public static void deleteStudent(Scanner sc) {
	        System.out.print("Enter Roll No to Delete: ");
	        String roll = sc.nextLine();
	        List<String> students = new ArrayList<>();
	        boolean deleted = false;

	        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                if (data[0].equals(roll)) {
	                    deleted = true;
	                } else {
	                    students.add(line);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error deleting student: " + e.getMessage());
	        }

	        if (deleted) {
	            try (FileWriter fw = new FileWriter(FILE)) {
	                for (String s : students) fw.write(s + "\n");
	            } catch (IOException e) {
	                System.out.println("Error writing file: " + e.getMessage());
	            }
	            System.out.println("Student Deleted Successfully!");
	        } else {
	            System.out.println("Student Not Found!");
	        }
	    }
}
