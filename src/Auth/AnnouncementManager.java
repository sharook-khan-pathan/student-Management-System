package Auth;

import java.io.*;
import java.util.*;

public class AnnouncementManager {

    private static final String FILE = "C:\\Users\\DELL\\eclipse-workspace\\StudentManagmentSystem\\src\\Auth\\accouncement";

    // 🔹 Admin posts an announcement
    public static void postAnnouncement(Scanner sc) {
        System.out.print("Enter Announcement Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Announcement Message: ");
        String message = sc.nextLine();

        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(title + " - " + message + "\n");
            System.out.println(" Announcement Posted Successfully!");
        } catch (IOException e) {
            System.out.println("Error writing announcement: " + e.getMessage());
        }
    }

    // 🔹 Students view announcements
    public static void viewAnnouncements() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            System.out.println("\n--- Announcements ---");
            while ((line = br.readLine()) != null) {
                System.out.println("-> " + line);
            }
        } catch (IOException e) {
            System.out.println("No announcements found.");
        }
    }
}
