package hotel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class room {

    public void aRoom() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n------------------");
            System.out.println("| Room Interface |");
            System.out.println("------------------\n");
            System.out.println("1. Add Room");
            System.out.println("2. View Room");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
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

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you, See you!!");
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
    double expectedPrice = 0;
    String formattedRoomType = ""; 

    
    while (!validRoomType) {
        System.out.print("Enter Room Type: ");
        rType = sc.nextLine().trim().replaceAll("\\s+", ""); 

        
        if (rType.equalsIgnoreCase("StandardRoom")) {
            expectedPrice = standardRoomPrice;
            formattedRoomType = "Standard Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("DeluxeRoom")) {
            expectedPrice = deluxeRoomPrice;
            formattedRoomType = "Deluxe Room";
            validRoomType = true;
        } else if (rType.equalsIgnoreCase("SuiteRoom")) {
            expectedPrice = suiteRoomPrice;
            formattedRoomType = "Suite Room";
            validRoomType = true;
        } else {
            System.out.println("Invalid room type. Please enter one of the following: Standard Room, Deluxe Room, Suite Room.");
        }
    }

    System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos/Hourly)");
    System.out.println("Deluxe Room (* 500 * Pesos/Hourly)");
    System.out.println("Suite Room (*750* Pesos/Hourly)");

    boolean validPrice = false;
    double rPrice = 0;

   
    while (!validPrice) {
        System.out.print("Enter Room Price: ");
        
        try {
            rPrice = sc.nextDouble();

          
            if (rPrice == expectedPrice) {
                validPrice = true;
            } else {
                System.out.println("Invalid price. The price for the " + formattedRoomType + " should be " + expectedPrice + " Pesos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value for the price.");
            sc.next(); 
        }
    }

    String sql = "INSERT INTO tbl_room (r_type, r_price) VALUES (?, ?)";

    config conf = new config();
    conf.addRecord(sql, formattedRoomType, rPrice);  

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
        System.out.println("\n//CHOICES//\n\n* Standard Room *  (300 square feet)");
        System.out.println("* Deluxe Room   *  (600 square feet)");
        System.out.println("* Suite Room    *  (1200 square feet)");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();
        
        System.out.print("New Room Type: ");
        String nRtype = sc.next();
        
        System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos/Hourly)");
        System.out.println("Deluxe Room (* 500 * Pesos/Hourly)");
        System.out.println("Suite Room (*750* Pesos/Hourly)'n");
        
        System.out.print("New Room Price: ");
        String nPrice = sc.next();

        String qry = "UPDATE tbl_room SET r_type = ?, r_price = ? WHERE r_id = ?";

        config conf = new config();
        conf.updateRecord(qry, nRtype, nPrice, id);
        
    }

    public void deleteRoom() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_room WHERE r_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
    }
}