package hotel;

import java.util.Scanner;

public class admin {

    public void editStatus() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================");
            System.out.println("||   Admin Interface    ||");
            System.out.println("==========================");
            System.out.println("||    1. Edit Status    ||");
            System.out.println("||    2. View Status    ||");
            System.out.println("||    3. Main Menu      ||");
            System.out.println("==========================\n");

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

            switch (action) {
                case 1:
                    editRoomStatus();
                    break;
                case 2:
                    viewRoomStatus();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
    public void editRoomStatus() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    int bID = 0;
    admin adm = new admin();
    adm.viewRoomStatus();

    while (true) {
        try {
            System.out.print("\nEnter Booking ID: ");
            bID = sc.nextInt();
            String bksID = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
            if (conf.getSingleValue(bksID, bID) != 0) {
                break;  
            } else {
                System.out.print("Booking ID does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Booking ID must be an integer. Enter again: ");
            sc.next(); 
        }
    }
    sc.nextLine(); 

    
    String currentStatusQuery = "SELECT b_status FROM tbl_bookings WHERE b_id = ?";
    String currentStatus = (String) conf.getSingleStringValue(currentStatusQuery, bID);

    if ("approved".equalsIgnoreCase(currentStatus)) {
       
        System.out.println("Booking ID " + bID + " is already approved. No further changes can be made.");
    } else {
        
        String updateStatusQuery = "UPDATE tbl_bookings SET b_status = ? WHERE b_id = ?";
        try {
            conf.updateRecord(updateStatusQuery, "approved", bID);
            System.out.println("Booking ID " + bID + " has been successfully approved!");
        } catch (Exception e) {
            System.out.println("Error updating booking status: " + e.getMessage());
        }
    }
}


    public void viewRoomStatus() {
        
        config conf = new config();
        
        String qry = "SELECT b_id, g_fname, g_lname, b_status FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id";
        String[] hdrs = {"B_ID", "Guest Fname","Guest Lname", "Booking Status"};
        String[] clmn = {"b_id", "g_fname","g_lname", "b_status"};
    
        conf.viewRecords(qry, hdrs, clmn);
       
    }
    
}