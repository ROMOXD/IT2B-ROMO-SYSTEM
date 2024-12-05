package hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    adm.viewRoomStatus();  // Display current room statuses

    // Get a valid Booking ID from user input
    while (true) {
        try {
            System.out.print("\nEnter Booking ID: ");
            bID = sc.nextInt();
            String bksID = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
            if (conf.getSingleValue(bksID, bID) != 0) {
                break;  // Valid ID, exit loop
            } else {
                System.out.print("Booking ID does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Enter again: ");
            sc.next();  // Clear invalid input
        }
    }
    sc.nextLine();  // Consume newline character

    // Fetch current booking status and room details
    String currentStatusQuery = "SELECT b_status, r_id, b_cin, b_cout FROM tbl_bookings WHERE b_id = ?";
    Object[] bookingDetails = conf.getMultipleValues(currentStatusQuery, bID);

    String currentStatus = (String) bookingDetails[0];
    int rID = (int) bookingDetails[1];
    String checkIn = (String) bookingDetails[2];
    String checkOut = (String) bookingDetails[3];

    // Parse the check-in and check-out dates
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false); // Strict parsing
    Date checkInDate = null;
    Date checkOutDate = null;

    try {
        if (checkIn != null && !checkIn.trim().isEmpty() && checkOut != null && !checkOut.trim().isEmpty()) {
            checkInDate = sdf.parse(checkIn);
            checkOutDate = sdf.parse(checkOut);
        } else {
            System.out.println("Error: Check-In or Check-Out date is null or empty.");
            return;
        }
    } catch (ParseException e) {
        System.out.println("Error parsing dates: Ensure the dates are in the correct format (DD-MM-YYYY).");
        e.printStackTrace(); // To help with debugging
        return;
    }

    // Convert Date objects to String for SQL comparison
    String checkInStr = sdf.format(checkInDate);
    String checkOutStr = sdf.format(checkOutDate);

    // Check for overlapping bookings
    String overlappingBookingQuery = "SELECT COUNT(*) FROM tbl_bookings WHERE r_id = ? " +
                                     "AND b_status = 'approved' AND (" +
                                     "(b_cin BETWEEN ? AND ?) OR " +
                                     "(b_cout BETWEEN ? AND ?) OR " +
                                     "(b_cin <= ? AND b_cout >= ?))";

    int overlapCount = conf.getSglValue(overlappingBookingQuery, rID, checkInStr, checkOutStr, checkInStr, checkOutStr, checkInStr);

    // Check if there are any overlapping bookings
    if (overlapCount > 0) {
        System.out.println("This room has overlapping approved bookings and cannot be approved.");
    } else {
        // Proceed with the approval if there is no overlap
        if ("approved".equalsIgnoreCase(currentStatus)) {
            System.out.println("Booking ID " + bID + " is already approved.");
        } else {
            // Update status to "approved"
            String updateStatusQuery = "UPDATE tbl_bookings SET b_status = ? WHERE b_id = ?";
            try {
                conf.updateRecord(updateStatusQuery, "approved", bID);
                System.out.println("Booking ID " + bID + " approved!");  // Success message

                // Now update room status to 'Booked' after approval
                String updateRoomStatusQuery = "UPDATE tbl_room SET r_status = 'Booked' WHERE r_id = ?";
                conf.updateRecord(updateRoomStatusQuery, rID);
                System.out.println("Room ID " + rID + " is now marked as 'Booked'.");
            } catch (Exception e) {
                System.out.println("Error updating status: " + e.getMessage());
            }
        }
    }
}


public void viewRoomStatus() {
    // Initialize config instance
    config conf = new config();

    // Query for booking information with guest details
    String qry = "SELECT b_id, g_fname, g_lname, b_status FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id";
    
    // Define table headers and columns
    String[] hdrs = {"B_ID", "Guest Fname", "Guest Lname", "Booking Status"};
    String[] clmn = {"b_id", "g_fname", "g_lname", "b_status"};

    // Display the booking status records
    conf.viewRecords(qry, hdrs, clmn);
}
    
}