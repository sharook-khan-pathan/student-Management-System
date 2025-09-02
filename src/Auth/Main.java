package Auth;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    if (AdminLogin.login()) {
                        AdminMenu.show(sc);   // ✅ now calls a separate class
                    }
                }
                case 2 -> {
                    if (StudentLogin.login(sc)) {
                        StudentMenu.show(sc); // ✅ now calls a separate class
                    }
                }
                case 0 -> {
                    System.out.println("👋 Exiting System...");
                    sc.close();
                    return;
                }
                default -> System.out.println("⚠ Invalid choice! Try again.");
            }
        }
    }
}
