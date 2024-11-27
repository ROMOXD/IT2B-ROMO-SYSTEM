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

    public void addRoom() {
    System.out.println("\n//CHOICES//\n\n* Standard Room *  (300 square feet)");
    System.out.println("* Deluxe Room   *  (600 square feet)");
    System.out.println("* Suite Room    *  (1200 square feet)\n");

    Scanner sc = new Scanner(System.in);
    String rType = "";
    boolean validRoomType = false;

   
    double standardRoomPrice = 300;
    double deluxeRoomPrice = 500;
    double suiteRoomPrice = 750;
    double price = 0;
    String roomType = "";

   
    while (!validRoomType) {
        System.out.print("Enter Room Type: ");
        rType = sc.nextLine().trim().replaceAll("\\s+", "");

       
        if (rType.equalsIgnoreCase("StandardRoom")) {
            price = standardRoomPrice;
            roomType = "Standard Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("DeluxeRoom")) {
            price = deluxeRoomPrice;
            roomType = "Deluxe Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("SuiteRoom")) {
            price = suiteRoomPrice;
            roomType = "Suite Room";
            validRoomType = true;
        } else {
            System.out.println("Invalid room type. Please enter one of the following: Standard Room, Deluxe Room, Suite Room.");
        }
    }

    System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos");
    System.out.println("Deluxe Room (* 500 * Pesos)");
    System.out.println("Suite Room (*750* Pesos)");

    boolean validPrice = false;
    double rPrice = 0;

   
    while (!validPrice) {
        System.out.print("Enter Room Price: ");
       
        try {
            rPrice = sc.nextDouble();

         
            if (rPrice == price) {
                validPrice = true;
            } else {
                System.out.println("Invalid price. The price for the " + roomType + " should be " + price + " Pesos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value for the price.");
            sc.next();
        }
    }

    String sql = "INSERT INTO tbl_room (r_type, r_price) VALUES (?, ?)";

    config conf = new config();
    conf.addRecord(sql, roomType, rPrice);  

    System.out.println("Room added successfully!");
}


    public void viewRoom() {
        String qry = "SELECT * FROM tbl_room";
        String[] hdrs = {"ID", "Room Type", "Room Price"};
        String[] clmn = {"r_id", "r_type", "r_price"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }

public void updateRoom() {

    room rm = new room();
    rm.viewRoom();
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    int id = 0;
    while (true) {
        try {
            System.out.print("Enter Room ID: ");
            id = sc.nextInt();
            String rmID = "SELECT COUNT(*) FROM tbl_room WHERE r_id = ?";
            if (conf.getSingleIntValue(rmID, id) > 0) { // Check if room exists
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

    // Fetch the current room type
    String currRoomTypeQuery = "SELECT r_type FROM tbl_room WHERE r_id = ?";
    String currRoomType = conf.getSingleStringValue(currRoomTypeQuery, id);

    System.out.println("\n//CHOICES//\n\n* Standard Room *  (300 square feet)");
    System.out.println("* Deluxe Room   *  (600 square feet)");
    System.out.println("* Suite Room    *  (1200 square feet)\n");

    String rType = "";
    boolean validRoomType = false;

    double standardRoomPrice = 300;
    double deluxeRoomPrice = 500;
    double suiteRoomPrice = 750;
    double NPrice = 0;
    String NRoomType = "";

    while (!validRoomType) {
        System.out.print("Enter New Room Type: ");
        rType = sc.nextLine().trim().replaceAll("\\s+", "");

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

        // Check if the new room type is the same as the current room type
        if (NRoomType.equalsIgnoreCase(currRoomType)) {
            System.out.println("The new room type is the same as the current room type (" + currRoomType + "). Please choose a different room type.");
        } else {
            validRoomType = true;
        }
    }

    boolean validPrice = false;
    double rPrice = 0;
    
    System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos");
    System.out.println("Deluxe Room (* 500 * Pesos)");
    System.out.println("Suite Room (*750* Pesos)\n");

    while (!validPrice) {
        System.out.print("Enter New Room Price: ");

        try {
            rPrice = sc.nextDouble();

            // Validate if the price matches the selected room type's price
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

    // Update query
    String qry = "UPDATE tbl_room SET r_type = ?, r_price = ? WHERE r_id = ?";
    conf.updateRecord(qry, NRoomType, NPrice, id);

    System.out.println("Room updated successfully!");
}


    public void deleteRoom() {
        
        Scanner sc = new Scanner(System.in);
        room rm = new room();
        rm.viewRoom();

        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_room WHERE r_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
       
        System.out.println("Room deleted successfully!");
    }
}