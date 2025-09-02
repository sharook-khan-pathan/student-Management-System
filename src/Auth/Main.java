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
                        
                    	while (true) {
                    	    System.out.println("\n--- Admin Menu ---");
                    	    System.out.println("1. Add Student");
                    	    System.out.println("2. Search Student");
                    	    System.out.println("3. Update Student");
                    	    System.out.println("4. Delete Student");
                    	    System.out.println("5.Task Assignment");
                    	    System.out.println("6. View Students Tasks");
                    	    System.out.println("0. Logout");
                    	    System.out.print("Choose: ");
                    	    int ch = sc.nextInt(); 

                    	    switch (ch) {
                    	        case 1 -> StudentCRUD.addStudent(sc);
                    	        case 2 -> StudentCRUD.searchStudent(sc);
                    	        case 3 -> StudentCRUD.updateStudent(sc);
                    	        case 4 -> StudentCRUD.deleteStudent(sc);
                    	        case 5 -> TaskAssignment.addAssignment(sc);
                    	        case 6 -> TaskAssignment.viewTasks(sc);
                    	        case 0 -> { return; }
                    	        default -> System.out.println("Invalid choice!");
                    	    }
                    	}
                    }
                }
                case 2 -> {
                    if (StudentLogin.login(sc)) {
                        
                    	while(true) {
                    		System.out.println("1. View Tasks");
                    		System.out.println("2. Update Task Status");
                    		System.out.println("0. Logout");
                    		System.out.print("Choose: ");
                    	    int ch = sc.nextInt();
                    	    
                    	    switch(ch) {
                    	    case 1 -> TaskAssignment.viewTasks(sc);
               
                    	    case 2 -> TaskAssignment.updateTaskStatus(sc);
                    	    	
                    	    case 0  -> { return; }
                    	    default -> System.out.println("Invalid choice!");
         
                    	    }
                    	}
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
