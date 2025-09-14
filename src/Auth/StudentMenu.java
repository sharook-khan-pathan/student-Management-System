package Auth;

import java.util.Scanner;

public class StudentMenu {
    public static void show(Scanner sc) {
        int ch;

        while (true) {
            System.out.println("\n--- 🎓 Student Menu ---");
            System.out.println("1. View Tasks");
            System.out.println("2. Update Task Status");
            System.out.println("3. View Marksheet");
            System.out.println("4. View Announcements");
            System.out.println("0. Logout");
            System.out.print("Choose: ");

            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid choice! Please enter a number.");
                continue;
            }

            switch (ch) {
                case 1 -> TaskAssignment.viewTasks(sc);
                case 2 -> TaskAssignment.updateTaskStatus(sc);
                case 3 -> {
 //                   System.out.print("Enter your Roll No: ");
 //                   String roll = sc.nextLine();
                    MarksManager.viewMarksheet(sc);
                }
                case 4 -> AnnouncementManager.viewAnnouncements();
                case 0 -> {
                    System.out.println("🔒 Logging out Student...");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice!");
            }
        }
    }
}

