package hotel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class room {

    public void aRoom() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================");
            System.out.println("||    Room Interface    ||");
            System.out.println("==========================");
            System.out.println("||    1. Add Room       ||");
            System.out.println("||    2. View Room      ||");
            System.out.println("||    3. Update Room    ||");
            System.out.println("||    4. Delete Room    ||");
            System.out.println("||    5. Main Menu      ||");
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

            room rm = new room();

            switch (action) {
                case 1:
                    rm.addRoom();
                    break;
                case 2:
                    rm.viewRoom();
                    break;
                case 3:
                    rm.updateRoom();
                    break;
                case 4:
                    rm.deleteRoom();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;
            }
        }
    }

    // Add a new room to the system
public void addRoom() {
    System.out.println("\n// ROOM TYPES //");
    System.out.println("* Standard Room *  (300 Pesos per night)");
    System.out.println("* Deluxe Room   *  (500 Pesos per night)");
    System.out.println("* Suite Room    *  (750 Pesos per night)\n");

    Scanner sc = new Scanner(System.in);
    String rType = "";
    boolean validRoomType = false;

    double standardRoomPrice = 300;
    double deluxeRoomPrice = 500;
    double suiteRoomPrice = 750;
    double price = 0;
    String roomType = "";

    while (!validRoomType) {
        System.out.print("Enter Room Type (Standard Room, Deluxe Room, Suite Room): ");
        rType = sc.nextLine().trim();

        if (rType.equalsIgnoreCase("Standard Room")) {
            price = standardRoomPrice;
            roomType = "Standard Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("Deluxe Room")) {
            price = deluxeRoomPrice;
            roomType = "Deluxe Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("Suite Room")) {
            price = suiteRoomPrice;
            roomType = "Suite Room";
            validRoomType = true;
        } else {
            System.out.println("Invalid room type. Please enter one of the following: Standard Room, Deluxe Room, Suite Room.");
        }
    }

    boolean validPrice = false;
    double rPrice = 0;

    while (!validPrice) {
        System.out.print("Confirm Room Price: ");
        try {
            rPrice = sc.nextDouble();

            if (rPrice == price) {
                validPrice = true;
            } else {
                System.out.println("Incorrect price. The price for " + roomType + " should be " + price + " Pesos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value for the price.");
            sc.next();
        }
    }

    String sql = "INSERT INTO tbl_room (r_type, r_price, r_status) VALUES (?, ?, ?)";
    String status = "Available";  // Set room status as "Available"

    config conf = new config();
    conf.addRecord(sql, roomType, rPrice, status);

    System.out.println("Room added successfully!");
}

// View all rooms in the system
public void viewRoom() {
    // SQL query to fetch room details
    String qry = "SELECT * FROM tbl_room";
    // Column headers for displaying room details
    String[] hdrs = {"ID", "Room Type", "Room Price", "Status"};
    // Column mappings from the database
    String[] clmn = {"r_id", "r_type", "r_price", "r_status"};

    config conf = new config();
    conf.viewRecords(qry, hdrs, clmn);
}

// Update the details of an existing room
public void updateRoom() {
    room rm = new room();
    rm.viewRoom(); // Display all rooms for user reference
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    int id = 0;

    // Validate room ID input
    while (true) {
        try {
            System.out.print("Enter Room ID: ");
            id = sc.nextInt();
            String rmID = "SELECT COUNT(*) FROM tbl_room WHERE r_id = ?";
            if (conf.getSingleIntValue(rmID, id) > 0) {
                break;
            } else {
                System.out.print("Room does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Room ID must be an integer. Enter again: ");
            sc.next();
        }
    }
    sc.nextLine();

    // Fetch the current room type for the given room ID
    String currRoomTypeQuery = "SELECT r_type FROM tbl_room WHERE r_id = ?";
    String currRoomType = conf.getSingleStringValue(currRoomTypeQuery, id);

    System.out.println("\n// CHOICES //\n\n* Standard Room *  (300 Pesos)");
    System.out.println("* Deluxe Room   *  (500 Pesos)");
    System.out.println("* Suite Room    *  (750 Pesos)\n");

    String rType = "";
    boolean validRoomType = false;

    // Predefined prices for room types
    double standardRoomPrice = 300;
    double deluxeRoomPrice = 500;
    double suiteRoomPrice = 750;
    double NPrice = 0;
    String NRoomType = "";

    // Validate the new room type input
    while (!validRoomType) {
        System.out.print("Enter New Room Type: ");
        rType = sc.nextLine().trim().replaceAll("\\s+", "");

        // Check for valid room type and assign corresponding price
        if (rType.equalsIgnoreCase("StandardRoom")) {
            NPrice = standardRoomPrice;
            NRoomType = "Standard Room";
        } else if (rType.equalsIgnoreCase("DeluxeRoom")) {
            NPrice = deluxeRoomPrice;
            NRoomType = "Deluxe Room";
        } else if (rType.equalsIgnoreCase("SuiteRoom")) {
            NPrice = suiteRoomPrice;
            NRoomType = "Suite Room";
        } else {
            System.out.println("Invalid room type. Please enter one of the following: Standard Room, Deluxe Room, Suite Room.");
            continue;
        }

        // Check if the new room type is different from the current room type
        if (NRoomType.equalsIgnoreCase(currRoomType)) {
            System.out.println("The new room type is the same as the current room type (" + currRoomType + "). Please choose a different room type.");
        } else {
            validRoomType = true;
        }
    }

    // Validate the new room price input
    boolean validPrice = false;
    double rPrice = 0;

    while (!validPrice) {
        System.out.print("Enter New Room Price: ");
        try {
            rPrice = sc.nextDouble();

            // Ensure the entered price matches the predefined price for the new room type
            if (rPrice == NPrice) {
                validPrice = true;
            } else {
                System.out.println("Invalid price. The price for the " + NRoomType + " should be " + NPrice + " Pesos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value for the price.");
            sc.next();
        }
    }

    // Update the room record in the database
    String qry = "UPDATE tbl_room SET r_type = ?, r_price = ? WHERE r_id = ?";
    conf.updateRecord(qry, NRoomType, NPrice, id);

    System.out.println("Room updated successfully!");
}

public void deleteRoom() {
    Scanner sc = new Scanner(System.in);
    room rm = new room();
    rm.viewRoom(); // Display all rooms for user reference

    int id = -1; // Initialize with an invalid ID
    boolean validInput = false; // Flag to track valid input

    while (!validInput) {
        try {
            System.out.print("Enter Room ID: ");
            id = sc.nextInt(); // Try to read an integer

            if (id < 0) { // Optionally validate the ID range
                throw new IllegalArgumentException("Room ID must be positive.");
            }
            validInput = true; // Input is valid
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            sc.nextLine(); // Clear the input buffer
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid Room ID.");
            sc.nextLine(); // Clear the input buffer
        }
    }

    // SQL query to delete a room by ID
    String qry = "DELETE FROM tbl_room WHERE r_id = ?";

    config conf = new config();
    try {
        conf.deleteRecord(qry, id); // Attempt to delete the record
        System.out.println("Room deleted successfully!");
    } catch (Exception e) {
        System.out.println("Failed to delete room: " + e.getMessage());
    }
}

}