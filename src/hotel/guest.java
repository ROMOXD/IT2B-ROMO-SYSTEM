package hotel;

import java.util.Scanner;

public class guest {

    public void aGuest() {
        Scanner sc = new Scanner(System.in);

        while (true) {
             System.out.println("\n==========================");
            System.out.println("||   Guest Interface    ||");
            System.out.println("==========================");
            System.out.println("||    1. Add Guest      ||");
            System.out.println("||    2. View Guest     ||");
            System.out.println("||    3. Update Guest   ||");
            System.out.println("||    4. Delete Guest   ||");
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

            guest gt = new guest();

            switch (action) {
                case 1:
                    gt.addGuest();
                    break;
                case 2:
                    gt.viewGuest();
                    break;
                case 3:
                    gt.updateGuest();
                    break;
                case 4:
                    gt.deleteGuest();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    
     public void addGuest() {
        
    Scanner sc = new Scanner(System.in);
        
        System.out.print("Guest First Name: ");
        String fname = sc.nextLine();
        while (!fname.matches("[a-zA-Z\\s\\-]+")) { 
            System.out.print("Invalid input! First name must not contain numbers or special characters. Enter again: ");
            fname = sc.nextLine();
        }
        System.out.print("Guest Last Name: ");
        String lname = sc.nextLine();
        while (!lname.matches("[a-zA-Z\\s\\-]+")) { 
            System.out.print("Invalid input! Last name must not contain numbers or special characters. Enter again: ");
            lname = sc.nextLine();
        }
        System.out.print("Guest Age (18+): ");
        int age = 0;
        while (true) {
        try {
        age = sc.nextInt();
        if (age >= 18) {
            break;
        } else {
            System.out.print("Invalid age!! Age must be 18 or older. Enter a valid age: ");
        }
    } catch (Exception e) {
        System.out.print("Invalid input!! Enter a valid number for age: ");
        sc.next(); 
        }
    }
        System.out.print("Guest Email: ");
        String email = sc.next();
        while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            System.out.print("Invalid email format! Please enter a valid email: ");
            email = sc.next();
        }
       System.out.print("Guest Status: ");
        String status = "";
        while (true) {
        try {
            status = sc.nextLine(); 
            if (status.matches(".*\\d.*")) { 
                throw new IllegalArgumentException("Status must not contain numbers!");
            }
            break; 
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); 
            System.out.print("Enter a valid status: "); 
        }
    }

        String sql = "INSERT INTO tbl_guest(g_fname,g_lname, g_age, g_email, g_status) VALUES (?, ?, ?, ?, ?)";

        config conf = new config();
        conf.addRecord(sql, fname, lname, age, email, status);
        
        System.out.println("Guest added successfully!");
    }
     
     public void viewGuest() {
        
        String qry = "SELECT * FROM tbl_guest";
        String[] hdrs = {"ID", "FName", "LName", "Age", "Email", "Status"};
        String[] clmn = {"g_id", "g_fname","g_lname", "g_age", "g_email", "g_status"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clmn);
    }
     
    public void updateGuest(){
    
    guest gt = new guest();
    gt.viewGuest();   
    config conf = new config();
    Scanner sc = new Scanner(System.in);
        
    int id = 0;
    while (true) {
        try {
            System.out.print("Enter Guest ID: ");
            id = sc.nextInt(); 
            String gstID = "SELECT g_id FROM tbl_guest WHERE g_id = ?";
            if (conf.getSingleValue(gstID, id) != 0) {
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
        System.out.print("Guest New First Name: ");
        String fname = sc.nextLine();
        while (!fname.matches("[a-zA-Z\\s\\-]+")) { 
            System.out.print("Invalid input! First name must not contain numbers or special characters. Enter again: ");
            fname = sc.nextLine();
        }
        System.out.print("Guest New Last Name: ");
        String lname = sc.nextLine();
        while (!lname.matches("[a-zA-Z\\s\\-]+")) { 
            System.out.print("Invalid input! Last name must not contain numbers or special characters. Enter again: ");
            lname = sc.nextLine();
        }
        System.out.print("Guest New Age (18+): ");
        int age = 0;
        while (true) {
        try {
        age = sc.nextInt();
        if (age >= 18) {
            break;
        } else {
            System.out.print("Invalid age!! Age must be 18 or older. Enter a valid age: ");
        }
    } catch (Exception e) {
        System.out.print("Invalid input!! Enter a valid number for age: ");
        sc.next(); 
        }
    }
        System.out.print("Guest New Email: ");
        String email = sc.next();
        while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            System.out.print("Invalid email format! Please enter a valid email: ");
            email = sc.next();
        }
       System.out.print("Guest New Status: ");
        String status = "";
        while (true) {
        try {
            status = sc.next(); 
            if (status.matches(".*\\d.*")) { 
                throw new IllegalArgumentException("Status must not contain numbers!");
            }
            break; 
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); 
            System.out.print("Enter a valid new status: "); 
        }
    }
        String qry = "UPDATE tbl_guest SET g_fname = ?, g_lname = ?, g_age = ?, g_email = ?, g_status = ? WHERE g_id = ?";

        conf.updateRecord(qry, fname, lname, age, email, status, id);
        
        System.out.println("Guest updated successfully!");
    }
    
    public void deleteGuest(){
        
        guest gt = new guest();
        gt.viewGuest(); 
        Scanner sc = new Scanner(System.in);
    
        System.out.print("Enter Guest ID: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM tbl_guest WHERE g_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);   
    
        System.out.println("Guest deleted successfully!");
    }  
    
}
