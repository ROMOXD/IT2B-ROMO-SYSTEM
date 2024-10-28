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
        
        System.out.println("\n//CHOICES//\n\n* Standard Room *  (300 square feet)");
        System.out.println("* Deluxe Room   *  (600 square feet)");
        System.out.println("* Suite Room    *  (1200 square feet)");
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nEnter Room Type: ");
        String rType = sc.nextLine();
        System.out.print("Enter Room Price: ");
        double rPrice = sc.nextDouble();

        String sql = "INSERT INTO tbl_room (r_type, r_price) VALUES (?, ?)";
        
        config conf = new config();
        conf.addRecord(sql, rType, rPrice);
        
         System.out.println("Room added successfully!");
         
        }
    
    public void viewBookings() {
        
        String qry = "SELECT * FROM tbl_room";
        String[] hdrs = {"ID", "Room Type", "Room Price"};
        String[] clmn = {"r_id", "r_type", "r_price"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }
    
    public void updateBookings(){
        
        System.out.println("\n//CHOICES//\n\n* Standard Room *  (300 square feet)");
        System.out.println("* Deluxe Room   *  (600 square feet)");
        System.out.println("* Suite Room    *  (1200 square feet)");
    
        Scanner sc = new Scanner(System.in);
    
        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();
        System.out.print("New Room Type: ");
        String nRtype = sc.next();
        System.out.print("New Room Price: ");
        String nPrice = sc.next();

        
        String qry = "UPDATE tbl_room SET r_type = ?, r_price = ? WHERE r_id = ?";
        
        config conf = new config();
        conf.updateRecord(qry, nRtype, nPrice, id);
    
    }
    
    public void deleteBookings(){
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_bookings WHERE b_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);   
    
    
    }
    
}