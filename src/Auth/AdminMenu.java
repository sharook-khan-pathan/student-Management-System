package Auth;

import java.util.Scanner;

public class AdminMenu {
    public static void show(Scanner sc) throws ClassNotFoundException {
        int ch;

        while (true) {
            System.out.println("\n--- 🛠 Admin Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Task Assignment");
            System.out.println("6. View Students Tasks");
            System.out.println("7. Enter Marks");
            System.out.println("8. View All Marks");
            System.out.println("9. Post Announcement");
            System.out.println("10. View Attendance");
            System.out.println("0. Logout");
            System.out.print("Choose: ");

            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid choice! Please enter a number.");
                continue;
            }

            switch (ch) {
                case 1 -> StudentCRUD.addStudent(sc);
                case 2 -> StudentCRUD.searchStudent(sc);
                case 3 -> StudentCRUD.updateStudent(sc);
                case 4 -> StudentCRUD.deleteStudent(sc);
                case 5 -> TaskAssignment.addAssignment(sc);
                case 6 -> TaskAssignment.viewTasks(sc);
                case 7 -> MarksManager.enterMarks(sc);
                case 8 -> MarksManager.viewMarksheet(sc);
                case 9 -> AnnouncementManager.postAnnouncement(sc);
                case 10 -> StudentLogin.viewAttendance(sc);
                case 0 -> {
                    System.out.println("🔒 Logging out Admin...");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice!");
            }
        }
    }
}
