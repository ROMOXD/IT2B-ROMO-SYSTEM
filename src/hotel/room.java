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
    
        System.out.print("Enter Room Type: ");
        String rType = sc.nextLine(); 

    System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos");
    System.out.println("Deluxe Room (* 500 * Pesos)");
    System.out.println("Suite Room (*750* Pesos)");

    System.out.print("\nEnter Room Price: ");
    double rPrice = sc.nextDouble();

    String sql = "INSERT INTO tbl_room (r_type, r_price) VALUES (?, ?)";

    config conf = new config();
    conf.addRecord(sql, rType, rPrice);  

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
 
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();
        sc.nextLine();
         
        System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos");
        System.out.println("Deluxe Room (* 500 * Pesos)");
        System.out.println("Suite Room (*750* Pesos)");
       
        System.out.print("\nNew Room Type: ");
        String nRtype = sc.nextLine();
        
        System.out.println("\n//CHOICES//\n\nStandard Room (* 300 * Pesos/Hourly)");
        System.out.println("Deluxe Room (* 500 * Pesos/Hourly)");
        System.out.println("Suite Room (*750* Pesos/Hourly)'n");
        
        System.out.print("\nNew Room Price: ");
        double nPrice = sc.nextDouble();

        String qry = "UPDATE tbl_room SET r_type = ?, r_price = ? WHERE r_id = ?";

        config conf = new config();
        conf.updateRecord(qry, nRtype, nPrice, id);
       
        System.out.println("Room updated successfully!");
    }

    public void deleteRoom() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_room WHERE r_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
        
        System.out.println("Room deleted successfully!");
    }
}