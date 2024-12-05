package hotel;

import java.util.Scanner;

public class report {

    public void aReport() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================");
            System.out.println("||   Report Interface   ||");
            System.out.println("==========================");
            System.out.println("|| 1. General Report    ||");
            System.out.println("|| 2. Individual Report ||");
            System.out.println("|| 3. Main Menu         ||");
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
                    genReport();
                    break;
                case 2:
                    indivReport();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
    public void genReport() {
    // Define the SQL query to fetch booking, guest, and room details
    String qry = "SELECT tbl_bookings.b_id, tbl_guest.g_id, tbl_guest.g_fname, tbl_guest.g_lname, "
               + "tbl_guest.g_status, tbl_guest.g_email, tbl_bookings.b_cin, tbl_bookings.b_cout, "
               + "tbl_room.r_type "
               + "FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";
    
    // Define headers and columns for the report
    String[] hdrs = {"B_ID", "G_ID", "Guest Fname", "Guest Lname", "Guest Status", "Guest Email", "Room Type", "Check-In", "Check-Out"};
    String[] clmn = {"b_id", "g_id", "g_fname", "g_lname", "g_status", "g_email", "r_type", "b_cin", "b_cout"};
    
    // Call the method to display the records
    config conf = new config();
    conf.viewRecords(qry, hdrs, clmn);
}

public void indivReport() {
    // Display guest details and prompt for Guest ID
    guest gt = new guest();
    gt.viewGuest();
    
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    int gid = 0;

    // Validate Guest ID input
    while (true) {
        try {
            System.out.print("Enter Guest ID: ");
            gid = sc.nextInt();
            String gstID = "SELECT COUNT(*) FROM tbl_guest WHERE g_id = ?";
            int count = conf.getSingleIntValue(gstID, gid);
            if (count > 0) break;  // Guest exists
            else System.out.println("Guest does not exist. Try again.");
        } catch (Exception e) {
            System.out.println("Invalid input! Enter again.");
            sc.next();
        }
    }
    sc.nextLine();  // Consume newline

    // Fetch and display guest name
    String gstNameQry = "SELECT g_fname || ' ' || g_lname FROM tbl_guest WHERE g_id = ?";
    String guestName = conf.getSingleStringValue(gstNameQry, gid);

    // Display report header
    System.out.println("=================================================================");
    System.out.println("\t\t\tIndividual Report");
    System.out.println("=================================================================");
    System.out.println("RECEIPT");
    System.out.println("Guest Name: " + (guestName != null ? guestName : "Unknown"));
    System.out.println("Guest ID: " + gid);
    System.out.println("=================================================================");

    // Query for guest's bookings
    String qry = "SELECT tbl_bookings.b_id AS Booking_ID, tbl_room.r_type AS Room_Type, tbl_room.r_price AS Room_Price, "
               + "tbl_bookings.b_cin AS Check_In, tbl_bookings.b_cout AS Check_Out, tbl_bookings.b_total AS Total, "
               + "tbl_bookings.b_cash AS Cash, tbl_bookings.b_change AS Change, tbl_bookings.b_status AS Status "
               + "FROM tbl_bookings "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id "
               + "WHERE tbl_bookings.g_id = ?";

    // Define headers and columns for the individual report
    String[] hdrs = {"Booking ID", "Room Type", "Room Price", "Check-In", "Check-Out", "Total", "Cash", "Change", "Status"};
    String[] clmn = {"Booking_ID", "Room_Type", "Room_Price", "Check_In", "Check_Out", "Total", "Cash", "Change", "Status"};

    // Call the method to display individual records
    conf.viewIndivRecords(qry, hdrs, clmn, gid);
    }
    
}