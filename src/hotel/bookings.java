package hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        
         int gID = 0;
         guest gt = new guest();
         gt.viewGuest();
    while (true) {
        try {
         
            System.out.print("\nEnter Guest ID: ");
            gID = sc.nextInt();
            String gstID = "SELECT g_id FROM tbl_guest WHERE g_id = ?";
            if (conf.getSingleValue(gstID, gID) != 0) {
                break;
            } else {
                System.out.print("Guest does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Guest ID must be an integer. Enter again: ");
            sc.next();
        }
    }
    sc.nextLine();

    int rID = 0;
    room rm = new room();
    rm.viewRoom();

while (true) {
    try {
        System.out.print("Enter Room ID: ");
        rID = sc.nextInt();  
        
        String rmID = "SELECT r_id FROM tbl_room WHERE r_id = ?";
        if (conf.getSingleValue(rmID, rID) != 0) { 
            break;
        } else {
            System.out.print("Room does not exist. Try again: ");
        }
    } catch (Exception e) {
        System.out.print("Invalid input! Room ID must be an integer. Enter again: ");
        sc.nextLine(); 
    }
}
sc.nextLine();
        
        String rmprice = "SELECT r_price FROM tbl_room WHERE r_id = ?";
        double price = conf.getSingleValue(rmprice, rID);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

    String cIn = "";
    while (true) {
        System.out.print("Enter Check/In (DD-MM-YYYY): ");
        cIn = sc.next();
        try {
            Date checkInDate = sdf.parse(cIn); 
            break; 
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/In date (DD-MM-YYYY): ");
        }
    }

    String cOut = "";
    while (true) {
        System.out.print("Enter Check/Out (DD-MM-YYYY): ");
        cOut = sc.next();
        try {
            Date checkOutDate = sdf.parse(cOut);
            Date checkInDate = sdf.parse(cIn); 
            
            if (checkOutDate.after(checkInDate)) {
                break; 
            } else {
                System.out.println("Check-Out date must be after Check-In date! Try again.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/Out date (DD-MM-YYYY): ");
        }
    }
        
        double total = price;
        System.out.print("Total Payment: "+total);
 
        System.out.print("\nCash: ");
    double csh = 0;
    while (true) {
    try {
        csh = sc.nextDouble();
        if (csh < total) {
            System.out.print("Enter enough amount!!: ");
            continue;
        }
        break; 
    } catch (Exception e) {
        System.out.println("Invalid input! Cash must be a valid number (decimal). Please enter a valid amount.");
        sc.next(); 
    }
}
        double cng = csh - total;      
        System.out.print("Change: "+cng);
        
        String status = "Pending!!";
        
        String sql = "INSERT INTO tbl_bookings(g_id, r_id, b_cin, b_cout, b_total, b_cash, b_change, b_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        conf.addRecord(sql, gID, rID, cIn, cOut, total, csh, cng, status);
        
        System.out.println("Bookings added successfully!");
        
        }
    
    public void viewBookings() {
        
        String qry = "SELECT b_id, g_fname, g_lname, r_type, b_cin, b_cout,b_status FROM tbl_bookings "
            + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
            + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";
        String[] hdrs = {"B_ID","First Name", "Last Name", "Room Type", "Check-In", "Check-Out", "Status"};
        String[] clmn = {"b_id", "g_fname", "g_lname", "r_type", "b_cin", "b_cout", "b_status"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }
    
    public void updateBookings() {
    
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    
    int bID = 0;
         bookings bk = new bookings();
         bk.viewBookings();
    while (true) {
        try {
         
            System.out.print("\nEnter Booking ID: ");
            bID = sc.nextInt();
            String bksID = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
            if (conf.getSingleValue(bksID, bID) != 0) {
                break;
            } else {
                System.out.print("Booking history does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Guest ID must be an integer. Enter again: ");
            sc.next();
        }
    }
    sc.nextLine();
    
    String rmID = "SELECT r_id FROM tbl_room WHERE r_id = ?";
    String rmprice = "SELECT r_price FROM tbl_room WHERE r_id = ?";
    String currRoom = "SELECT r_id FROM tbl_bookings WHERE b_id = ?";
    
    int currRoomID = (int) conf.getSingleValue(currRoom, bID);
    
        room rm = new room();
        rm.viewRoom();
     int romID = 0;
    while (true) {
        try {
            System.out.print("Enter New Room ID: ");
            romID = sc.nextInt();

            if (romID == currRoomID) {
                System.out.print("This is the current room ID. Please enter a different valid room ID: ");
            } else if (conf.getSingleValue(rmID, romID) == 0) {
                System.out.print("Room does not exist. Try again: ");
            } else {
                break;
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Room ID must be an integer. Enter again: ");
            sc.nextLine();
        }
    }
    double price = conf.getSingleValue(rmprice, romID);
    
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

    String chkIn = "";
    while (true) {
        System.out.print("Enter Check/In (DD-MM-YYYY): ");
        chkIn = sc.next();
        try {
            Date checkInDate = sdf.parse(chkIn); 
            break; 
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/In date (DD-MM-YYYY): ");
        }
    }

    String chkOut = "";
    while (true) {
        System.out.print("Enter Check/Out (DD-MM-YYYY): ");
        chkOut = sc.next();
        try {
            Date checkOutDate = sdf.parse(chkOut);
            Date checkInDate = sdf.parse(chkIn); 
            
            if (checkOutDate.after(checkInDate)) {
                break; 
            } else {
                System.out.println("Check-Out date must be after Check-In date! Try again.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/Out date (DD-MM-YYYY): ");
        }
    }
        
    double ttl = price;
    System.out.print("Total Payment: "+ttl);
 
        System.out.print("\nCash: ");
    double csh = 0;
    while (true) {
    try {
        csh = sc.nextDouble();
        if (csh < ttl) {
            System.out.print("Enter enough amount!!: ");
            continue;
        }
        break; 
    } catch (Exception e) {
        System.out.println("Invalid input! Cash must be a valid number (decimal). Please enter a valid amount.");
        sc.next(); 
    }
}
    double cng = csh - ttl;      
    System.out.print("Change: "+cng);
        System.out.println("\n");
        
    String status = "Pending!!";
    
    String qry = "UPDATE tbl_bookings SET r_id = ?, b_cin = ?, b_cout = ?, b_total = ?, b_cash = ?, b_change = ?, b_status = ? WHERE b_id = ?";

        
        conf.updateRecord(qry, romID, chkIn, chkOut, ttl, csh, cng, status, bID);
        
        System.out.println("Bookings updated successfully!");
 
        
        
    }
    
    public void deleteBookings(){
        
        Scanner sc = new Scanner(System.in);
        bookings bk = new bookings();
        bk.viewBookings();

        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_bookings WHERE b_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);   
    
    System.out.println("Bookings deleted successfully!");
    }
    
}