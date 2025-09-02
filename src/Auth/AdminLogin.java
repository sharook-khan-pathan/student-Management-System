package Auth;
import java.util.*;
 class AdminLogin {
	 	private static final String adminuser = "admin";	
	 	private static final String adminpass = "admin123";
	 public static boolean login() {

	        Scanner sc = new Scanner(System.in);

	        System.out.println("=== Admin Login ===");
	        System.out.print("Enter Username: ");
	        String username = sc.nextLine().trim();

	        System.out.print("Enter Password: ");
	        String password = sc.nextLine().trim();

	        //Authentication for Admin's Username and Password
	        if (username.isEmpty() || password.isEmpty()) {
	            System.out.println("Error: Username/Password required!");
	        } else if (username.equals(adminuser) && password.equals(adminpass)) {
	            System.out.println("Admin logged in successfully!");
	            return true;	            
	        } else {
	            System.out.println("Error: Invalid credentials!");
	        }
	        
	        return false;
	 }
	   
}
