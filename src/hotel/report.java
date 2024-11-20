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
        System.out.println("1. General Report");
        System.out.println("2. Individual Report");
        System.out.println("3. Main Menu");
        
        int action = 0; 
            boolean validInput = false;

            
            while (!validInput) {
                try {
                    System.out.print("Enter action: ");
                    action = sc.nextInt();
                    if (action < 1 || action > 3) {
                        throw new IllegalArgumentException("Invalid choice. Please choose between 1 and 3.");
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
                  rp.genReport(); 
            break;
                case 2:
                  rp.indivReport();
                    break;
            case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
            }
            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
       
    } while(response.equalsIgnoreCase("yes"));
            System.out.println("Thank you, See you!!");

    }
    
    public void genReport(){
    
    
    String qry = "SELECT b_id, g_fname, g_lname, g_status, g_email, b_cin, b_cout FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";
        String[] hdrs = {"B_ID", "Guest Fname","Guest Lname", "Guest Status","Guest Email", "Check-In", "Check-Out"};
        String[] clmn = {"b_id", "g_fname","g_lname", "g_status","g_email", "b_cin", "b_cout"};
    
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    };
    
    public void indivReport(){
    
    
    String qry = "SELECT b_id, g_fname, g_lname, g_status, g_email, r_type, b_cin, b_cout, b_total, b_cash, b_change, b_status FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";
        String[] hdrs = {"B_ID", "Guest Fname","Guest Lname", "Guest Status", "Guest Email", "Room Type", "Check-In", "Check-Out", "Total", "Cash", "Change", "Booking Status"};
        String[] clmn = {"b_id", "g_fname","g_lname", "g_status", "g_email", "r_type", "b_cin", "b_cout", "b_total", "b_cash", "b_change","b_status"};
    
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    };
    
    
}