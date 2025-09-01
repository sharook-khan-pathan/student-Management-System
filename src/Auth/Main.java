package Auth;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    if (AdminLogin.login()) {
                        // TODO: Admin menu (CRUD, tasks, etc.)
                    	while (true) {
                    	    System.out.println("\n--- Admin Menu ---");
                    	    System.out.println("1. Add Student");
                    	    System.out.println("2. Search Student");
                    	    System.out.println("3. Update Student");
                    	    System.out.println("4. Delete Student");
                    	    System.out.println("0. Logout");
                    	    System.out.print("Choose: ");
                    	    int ch = sc.nextInt(); 

                    	    switch (ch) {
                    	        case 1 -> StudentCRUD.addStudent(sc);
                    	        case 2 -> StudentCRUD.searchStudent(sc);
                    	        case 3 -> StudentCRUD.updateStudent(sc);
                    	        case 4 -> StudentCRUD.deleteStudent(sc);
                    	        case 0 -> { return; }
                    	        default -> System.out.println("Invalid choice!");
                    	    }
                    	}
                    }
                }
                case 2 -> {
                    if (StudentLogin.login(sc)) {
                        // TODO: Student menu (view announcements, update profile, etc.)
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }

	}

}
