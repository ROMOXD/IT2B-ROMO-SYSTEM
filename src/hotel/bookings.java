package hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class bookings {

    public void aBookings() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================");
            System.out.println("||  Bookings Interface  ||");
            System.out.println("==========================");
            System.out.println("||  1. Add Bookings     ||");
            System.out.println("||  2. View Bookings    ||");
            System.out.println("||  3. Update Bookings  ||");
            System.out.println("||  4. Delete Bookings  ||");
            System.out.println("||  5. Main Menu        ||");
            System.out.println("==========================\n");

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

            switch (action) {
                case 1:
                    addBookings();
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    updateBookings();
                    break;
                case 4:
                    deleteBookings();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
public void addBookings() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    // Step 1: Display guests and validate Guest ID
    int gID = 0;
    guest gt = new guest();
    gt.viewGuest();

    while (true) {
        try {
            System.out.print("\nEnter Guest ID: ");
            gID = sc.nextInt();
            String gstID = "SELECT g_id FROM tbl_guest WHERE g_id = ?";
            if (conf.getSingleValue(gstID, gID) != 0) { // Validate Guest ID exists
                break;
            } else {
                System.out.println("Guest does not exist. Try again.");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Guest ID must be an integer. Enter again: ");
            sc.next();
        }
    }
    sc.nextLine();

    // Step 2: Display rooms and validate Room ID
    int rID = 0;
    room rm = new room();
    rm.viewRoom();

    String latestCheckoutDate = null; // To store the latest checkout date if the room is already booked

    while (true) {
        try {
            System.out.print("Enter Room ID: ");
            rID = sc.nextInt();

            String rmID = "SELECT r_id FROM tbl_room WHERE r_id = ?";
            if (conf.getSingleValue(rmID, rID) == 0) { // Validate Room ID exists
                System.out.println("Room does not exist. Try again.");
                continue;
            }

            String roomStatusQuery = "SELECT r_status FROM tbl_room WHERE r_id = ?";
            String roomStatus = conf.getSingleStringValue(roomStatusQuery, rID);

            // Check if the room is already booked or approved
            if ("Booked".equalsIgnoreCase(roomStatus) || "Approved".equalsIgnoreCase(roomStatus)) {
                String latestBookingQuery = "SELECT MAX(b_cout) AS latest_cout FROM tbl_bookings WHERE r_id = ? AND (b_status = 'Pending!!' OR b_status = 'Approved')";
                latestCheckoutDate = conf.getLatestBookingDate(latestBookingQuery, rID);

                if (latestCheckoutDate != null) {
                    System.out.println("This Room is already booked. Latest booking ends on " + latestCheckoutDate + ".");
                }
            }
            break;
        } catch (Exception e) {
            System.out.print("Invalid input! Room ID must be an integer. Enter again: ");
            sc.nextLine();
        }
    }
    sc.nextLine();

    // Step 3: Fetch room price and validate booking dates
    String rmprice = "SELECT r_price FROM tbl_room WHERE r_id = ?";
    double price = conf.getSingleValue(rmprice, rID);

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false);

    // Validate Check-In Date
    String cIn = "";
    while (true) {
        System.out.print("Enter Check/In (DD-MM-YYYY): ");
        cIn = sc.next();
        try {
            Date checkInDate = sdf.parse(cIn);

            if (latestCheckoutDate != null) {
                Date latestDate = sdf.parse(latestCheckoutDate);
                if (!checkInDate.after(latestDate)) {
                    System.out.println("Invalid Check/In date! Must be after " + latestCheckoutDate + ". Try again.");
                    continue;
                }
            }
            break;
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/In date (DD-MM-YYYY).");
        }
    }

    // Validate Check-Out Date
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
            System.out.println("Invalid date format! Please enter a valid Check/Out date (DD-MM-YYYY).");
        }
    }

    // Step 4: Calculate total payment and validate cash
    double total = price;
    System.out.print("Total Payment: " + total);

    double csh = 0;
    while (true) {
        System.out.print("\nCash: ");
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

    double cng = csh - total; // Calculate change
    System.out.print("Change: " + cng);

    // Step 5: Insert booking record and update room status
    String status = "Pending!!";

    String sql = "INSERT INTO tbl_bookings(g_id, r_id, b_cin, b_cout, b_total, b_cash, b_change, b_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    conf.addRecord(sql, gID, rID, cIn, cOut, total, csh, cng, status);

    String updateRoomStatus = "UPDATE tbl_room SET r_status = 'Booked' WHERE r_id = ?";
    conf.updateRecord(updateRoomStatus, rID);

    System.out.println("Booking added successfully! Room ID " + rID + " is now marked as 'Booked'.");
}

    
    public void viewBookings() {
    // SQL query to fetch booking details joined with guest and room information
    String qry = "SELECT tbl_bookings.b_id, tbl_guest.g_fname, tbl_guest.g_lname, "
               + "tbl_bookings.r_id, tbl_room.r_type, tbl_bookings.b_cin, "
               + "tbl_bookings.b_cout, tbl_bookings.b_status "
               + "FROM tbl_bookings "
               + "LEFT JOIN tbl_guest ON tbl_guest.g_id = tbl_bookings.g_id "
               + "LEFT JOIN tbl_room ON tbl_room.r_id = tbl_bookings.r_id";

    // Headers for the displayed table
    String[] hdrs = {"B_ID", "First Name", "Last Name", "Room ID", "Room Type", "Check-In", "Check-Out", "Status"};
    // Corresponding column names from the database
    String[] clmn = {"b_id", "g_fname", "g_lname", "r_id", "r_type", "b_cin", "b_cout", "b_status"};

    config conf = new config();
    // Display booking records in a tabular format
    conf.viewRecords(qry, hdrs, clmn);
}
    
    public void updateBookings() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    // Step 1: Display bookings and validate Booking ID
    int bID = 0;
    bookings bk = new bookings();
    bk.viewBookings();

    while (true) {
        try {
            System.out.print("\nEnter Booking ID: ");
            bID = sc.nextInt();
            String bksID = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
            if (conf.getSingleValue(bksID, bID) != 0) { // Validate Booking ID exists
                break;
            } else {
                System.out.println("Booking history does not exist. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Booking ID must be an integer. Enter again.");
            sc.next();
        }
    }
    sc.nextLine();

    // Step 2: Get current room information
    String currRoomQuery = "SELECT r_id FROM tbl_bookings WHERE b_id = ?";
    int currRoomID = (int) conf.getSingleValue(currRoomQuery, bID);

    // Step 3: Display rooms and validate new Room ID
    room rm = new room();
    rm.viewRoom();

    int newRoomID = 0;
    String roomQuery = "SELECT r_id FROM tbl_room WHERE r_id = ?";
    String roomPriceQuery = "SELECT r_price FROM tbl_room WHERE r_id = ?";

    while (true) {
        try {
            System.out.print("Enter New Room ID: ");
            newRoomID = sc.nextInt();

            if (newRoomID == currRoomID) {
                System.out.println("This is the current room ID. Please enter a different valid room ID.");
            } else if (conf.getSingleValue(roomQuery, newRoomID) == 0) { // Validate Room ID exists
                System.out.println("Room does not exist. Try again.");
            } else {
                break;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Room ID must be an integer. Enter again.");
            sc.next(); 
        }
    }

    double roomPrice = conf.getSingleValue(roomPriceQuery, newRoomID);

    // Step 4: Validate new Check-In and Check-Out dates
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false);

    String checkInDate = "";
    while (true) {
        System.out.print("Enter Check/In (DD-MM-YYYY): ");
        checkInDate = sc.next();
        try {
            sdf.parse(checkInDate); // Ensure valid date format
            break;
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/In date (DD-MM-YYYY).");
        }
    }

    String checkOutDate = "";
    while (true) {
        System.out.print("Enter Check/Out (DD-MM-YYYY): ");
        checkOutDate = sc.next();
        try {
            Date checkOut = sdf.parse(checkOutDate);
            Date checkIn = sdf.parse(checkInDate);

            if (checkOut.after(checkIn)) {
                break;
            } else {
                System.out.println("Check-Out date must be after Check-In date! Try again.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter a valid Check/Out date (DD-MM-YYYY).");
        }
    }

    // Step 5: Calculate total payment and validate cash
    double totalPayment = roomPrice;
    System.out.println("Total Payment: " + totalPayment);

    double cash = 0;
    while (true) {
        System.out.print("\nCash: ");
        try {
            cash = sc.nextDouble();
            if (cash < totalPayment) {
                System.out.println("Insufficient amount! Please enter an amount greater than or equal to the total payment.");
            } else {
                break;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Cash must be a valid number. Enter again.");
            sc.next();
        }
    }

    double change = cash - totalPayment;

    // Step 6: Update booking record
    String status = "Pending!!";
    String updateQuery = "UPDATE tbl_bookings SET r_id = ?, b_cin = ?, b_cout = ?, b_total = ?, b_cash = ?, b_change = ?, b_status = ? WHERE b_id = ?";
    conf.updateRecord(updateQuery, newRoomID, checkInDate, checkOutDate, totalPayment, cash, change, status, bID);

    System.out.println("Booking updated successfully!");
}
    
    public void deleteBookings() {
    Scanner sc = new Scanner(System.in);
    bookings bk = new bookings();
    
    // Step 1: Display bookings for user reference
    bk.viewBookings();

    // Step 2: Get and validate Booking ID to delete
    System.out.print("Enter Booking ID: ");
    int id = sc.nextInt();

    String qry = "DELETE FROM tbl_bookings WHERE b_id = ?";

    config conf = new config();
    conf.deleteRecord(qry, id); // Execute the deletion query

    System.out.println("Booking deleted successfully!");
    }
    
}