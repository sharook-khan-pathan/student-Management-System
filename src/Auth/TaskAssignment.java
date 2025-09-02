package Auth;

import java.io.*;
import java.util.*;

public class TaskAssignment {
	
	private static String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\assignment.txt";
	
	//Admin Task Assignment
	public static void addAssignment(Scanner sc) {
		
		
		try {
            System.out.print("Enter Task ID: ");
            String taskId = sc.next();
            System.out.print("Enter Roll No of Student: ");
            String rollNo = sc.next();
            System.out.print("Enter Task Description: ");
            String desc = sc.next();

            try (FileWriter fw = new FileWriter(FILE, true)) {
                fw.write(taskId + "," + rollNo + "," + desc + ",Pending\n");
            }

            System.out.println("Task Assigned Successfully to Roll No: " + rollNo);
        } 
		catch (Exception e) {
            System.out.println("Error assigning task: " + e.getMessage());
        }
		
	}
	//Student View Task
	public static void viewTasks(Scanner sc) {
		
		System.out.print("Enter Roll.No:");
    	String roll = sc.next();
    	
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            boolean found = false;
            System.out.println(" Your Tasks:");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(roll)) {
                    System.out.println("Task ID: " + data[0] + " | " + "Description: " + data[2] + " | " + "Status: " + data[3]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println(" No tasks assigned yet.");
            }
        } catch (IOException e) {
            System.out.println(" Error while reading tasks: " + e.getMessage());
        }
	}
	
	//Update Task Status
    public static void updateTaskStatus(Scanner sc) {
    	System.out.print("Enter Roll.No:");
    	String roll1 = sc.next();
        try {
            System.out.print("Enter Task ID to update: ");
            String taskId = sc.next();
            System.out.print("Enter New Status (Pending/In Progress/Completed): ");
            String newStatus = sc.next();

            File inputFile = new File(FILE);
            File tempFile = new File("tasks_temp.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                 FileWriter fw = new FileWriter(tempFile)) {

                String line;
                boolean updated = false;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(taskId) && data[1].equals(roll1)) {
                        fw.write(data[0] + "," + data[1] + "," + data[2] + "," + newStatus + "\n");
                        updated = true;
                    } else {
                        fw.write(line + "\n");
                    }
                }

                if (updated) {
                    System.out.println("Task status updated successfully!");
                } else {
                    System.out.println("Task not found for your Roll No.");
                }
            }

           
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            System.out.println("Error while updating task: " + e.getMessage());
        }
    }
}

	

