package hotel;

import java.util.Scanner;

public class bookings {
    
    
    public void aBookings(){
       Scanner sc = new Scanner(System.in);    
     String response;
        
        do{
        System.out.println("\n----------------------");
        System.out.println("| Bookings Interface |");
        System.out.println("----------------------\n");
        System.out.println("1. Add Bookings");
        System.out.println("2. View Bookings");
        System.out.println("3. Update Bookings");
        System.out.println("4. Delete Bookings");
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
            
            bookings bk = new bookings();
            
            switch(action){
               case 1:
                  bk.addBookings(); 
                break;
               case 2:
                  bk.viewBookings();          
                break;   
                case 3:
                  bk.updateBookings();
                break;
                case 4:
                  bk.deleteBookings();
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
    
    public void addBookings(){
        
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nEnter Guest ID: ");
        int gID = sc.nextInt();
        
        String gstID = "SELECT g_id FROM tbl_guest WHERE g_id = ?";
        String rmID = "SELECT r_id FROM tbl_room WHERE r_id = ?";
        String rmprice = "SELECT r_price FROM tbl_room WHERE r_id = ?";

        
        while(conf.getSingleValue(gstID, gID) == 0){
        System.out.print("Guest does not exist try again!!: ");
            gID = sc.nextInt();     
        }
  
        System.out.print("Enter Room ID: ");
        int rID = sc.nextInt();
        
        while(conf.getSingleValue(rmID, rID) == 0){
        System.out.print("Room does not exist try again!!: ");
            rID = sc.nextInt();     
        }
        double price = conf.getSingleValue(rmprice, rID);
        
        System.out.print("Enter Room Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Enter Check/In(DD-MM-YYYY): ");
        String cIn = sc.next();
        System.out.print("Enter Check/Out(DD-MM-YYYY): ");
        String cOut = sc.next();
        
        double total = price * qty;
        System.out.print("Total Payment: "+total);
 
        System.out.print("\nCash: ");
        double csh = sc.nextDouble();
        
        while(csh < total){
            System.out.print("Enter enough amount!!: ");
            csh = sc.nextDouble();
        }
         double cng = csh - total;      
        System.out.print("Change: "+cng);
        
        String sql = "INSERT INTO tbl_bookings(g_id, r_id, b_qty, b_cin, b_cout, b_total, b_cash, b_change) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        conf.addRecord(sql, gID, rID, qty, cIn, cOut, total, csh, cng );
        
        System.out.println("Bookings added successfully!");
        
        }
    
    
    
    public void viewBookings() {
        
        String qry = "SELECT * FROM tbl_bookings";
        String[] hdrs = {"B_ID","G_ID", "R_ID", "Qty", "Check-In", "Check-Out", "Total", "Cash", "Change"};
        String[] clmn = {"b_id", "g_id", "r_id", "b_qty", "b_cin", "b_cout", "b_total", "b_cash", "b_change"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }
    
    public void updateBookings() {
    
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Enter Booking ID: ");
    int bID = sc.nextInt();
    
    String bksID = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
    String rmID = "SELECT r_id FROM tbl_room WHERE r_id = ?";
    String rmprice = "SELECT r_price FROM tbl_room WHERE r_id = ?";
    String currRoom = "SELECT r_id FROM tbl_bookings WHERE b_id = ?";
    
    while (conf.getSingleValue(bksID, bID) == 0) {
        System.out.print("Booking history does not exist. Try again: ");
        bID = sc.nextInt();
    }
    
    int currRoomID = (int) conf.getSingleValue(currRoom, bID);

    System.out.print("Enter New Room ID: ");
    int romID = sc.nextInt();

    while (conf.getSingleValue(rmID, romID) == 0 || romID == currRoomID) {
        if (romID == currRoomID) {
            System.out.print("This is the current room ID. Please enter a different valid room ID: ");
        } else {
            System.out.print("Room does not exist. Try again: ");
        }
        romID = sc.nextInt();
    }
    
    double price = conf.getSingleValue(rmprice, romID);
    
    System.out.print("Enter New Room Quantity: ");
    int quan = sc.nextInt();
    System.out.print("Enter New Check/In(DD-MM-YYYY): ");
    String chkIn = sc.next();
    System.out.print("Enter New Check/Out(DD-MM-YYYY): ");
    String chkOut = sc.next();
        
    double ttl = price * quan;
    System.out.print("Total Payment: "+ttl);
 
    System.out.print("\nCash: ");
    double csh = sc.nextDouble();
        
    while(csh < ttl){
    System.out.print("Enter enough amount!!: ");
     csh = sc.nextDouble();
}
    double cng = csh - ttl;      
    System.out.print("Change: "+cng);
        System.out.println("\n");
    String qry = "UPDATE tbl_bookings SET r_id = ?, b_qty = ?, b_cin = ?, b_cout = ?, b_total = ?, b_cash = ?, b_change = ? WHERE b_id = ?";

        
        conf.updateRecord(qry, romID, quan, chkIn, chkOut, ttl, csh, cng, bID);
        
        System.out.println("Bookings updated successfully!");
 
        
        
    }
    
    public void deleteBookings(){
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_bookings WHERE b_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);   
    
    System.out.println("Bookings deleted successfully!");
    }
    
}