package hotel;

import java.util.Scanner;

public class guest {
    
    
    public void aGuest(){
       Scanner sc = new Scanner(System.in);    
     String response;
        
        do{
        System.out.println("\n-------------------");
        System.out.println("| Guest Interface |");
        System.out.println("-------------------\n");
        System.out.println("1. Add Guest");
        System.out.println("2. View Guest");
        System.out.println("3. Update Guest");
        System.out.println("4. Delete Guest");
        System.out.println("5. Main Menu");
        
        int action = 0; 
            boolean validInput = false;

            
            while (!validInput) {
                try {
                    System.out.print("Enter action: ");
                    action = sc.nextInt();
                    if (action < 1 || action > 5) {
                        throw new IllegalArgumentException("Invalid choice. Please choose between 1 and 5.");
                    }
                    validInput = true; 
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                }
            }
            
            guest gt = new guest();
            
            switch(action){
               case 1:
                  gt.addGuest(); 
                break;
               case 2:
                  gt.viewGuest();
                break;   
                case 3:
                  gt.updateGuest();
                break;
                case 4:
                  gt.deleteGuest();
                break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;
            }
            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
       
    } while(response.equalsIgnoreCase("yes"));
            System.out.println("Thank you, See you!!");

    }
    
     public void addGuest() {
        
    Scanner sc = new Scanner(System.in);
        
        System.out.print("Guest Name: ");
        String name = sc.nextLine();
        System.out.print("Guest Age(18+): ");
        String age = sc.next();
        System.out.print("Guest Email: ");
        String email = sc.next();
        System.out.print("Guest Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_guest(g_name, g_age, g_email, g_status) VALUES (?, ?, ?, ?)";

        config conf = new config();
        conf.addRecord(sql, name, age, email, status);
        
        
    }
     
     public void viewGuest() {
        
        String qry = "SELECT * FROM tbl_guest";
        String[] hdrs = {"ID", "Name", "Age", "Email", "Status"};
        String[] clmn = {"g_id", "g_name", "g_age", "g_email", "g_status"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }
     
    public void updateGuest(){
    
    Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Guest ID: ");
        int id = sc.nextInt();
        System.out.print("Guest New Name: ");
        String name = sc.nextLine();
        System.out.print("Guest New Age(18+): ");
        String age = sc.next();
        System.out.print("Guest New Email: ");
        String email = sc.next();
        System.out.print("Guest New Status: ");
        String status = sc.next();

        String qry = "UPDATE tbl_guest SET g_name = ?, g_age = ?, g_email = ?, g_status = ? WHERE r_id = ?";

        config conf = new config();
        conf.addRecord(qry, name, age, email, status, id);
    
    }
    
    public void deleteGuest(){
        
        Scanner sc = new Scanner(System.in);
    
        System.out.print("Enter Guest ID: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_guest WHERE g_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);   
    
    }  
    
}
