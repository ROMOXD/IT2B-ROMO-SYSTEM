package hotel;

import java.util.Scanner;

public class report {
    
    
    public void aReport(){
       Scanner sc = new Scanner(System.in);    
     String response;
        
        do{
        System.out.println("\n-------------------");
        System.out.println("| Report Interface |");
        System.out.println("-------------------\n");
        System.out.println("1. View Report");
        System.out.println("2. Main Menu");
        
        int action = 0; 
            boolean validInput = false;

            
            while (!validInput) {
                try {
                    System.out.print("Enter action: ");
                    action = sc.nextInt();
                    if (action < 1 || action > 3) {
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
            
            report rp = new report();
            
            switch(action){
               case 1:
                  rp.viewReport(); 
                break;
                case 2:
                    System.out.println("Returning to Main Menu...");
                    return;
            }
            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
       
    } while(response.equalsIgnoreCase("yes"));
            System.out.println("Thank you, See you!!");

    }
    
    public void viewReport(){
    
    
    String qry = "SELECT b_id, g_name, g_status, r_type , b_cin,b_cout,b_total, b_cash, b_change FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";
        String[] hdrs = {"B_ID", "Guest Name", "Guest Status", "Room Type", "Check-In", "Check-Out", "Total", "Cash", "Change"};
        String[] clmn = {"b_id", "g_name", "g_status", "r_type", "b_cin", "b_cout", "b_total", "b_cash", "b_change"};
    
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    };
    
    
}