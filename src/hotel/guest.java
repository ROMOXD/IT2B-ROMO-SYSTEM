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
    
     // Method to add a new guest to the system
public void addGuest() {
    Scanner sc = new Scanner(System.in);
    
    // Get and validate guest's first name
    System.out.print("Guest First Name: ");
    String fname = sc.nextLine();
    while (!fname.matches("[a-zA-Z\\s\\-]+")) { 
        System.out.print("Invalid input! First name must not contain numbers or special characters. Enter again: ");
        fname = sc.nextLine();
    }
    
    // Get and validate guest's last name
    System.out.print("Guest Last Name: ");
    String lname = sc.nextLine();
    while (!lname.matches("[a-zA-Z\\s\\-]+")) { 
        System.out.print("Invalid input! Last name must not contain numbers or special characters. Enter again: ");
        lname = sc.nextLine();
    }
    
    // Get and validate guest's age (must be 18+)
    System.out.print("Guest Age (18+): ");
    int age = 0;
    while (true) {
        try {
            age = sc.nextInt();
            if (age >= 18) {
                break; // Valid age
            } else {
                System.out.print("Invalid age!! Age must be 18 or older. Enter a valid age: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input!! Enter a valid number for age: ");
            sc.next(); // Clear the invalid input
        }
    }
    
    // Get and validate guest's email address
    System.out.print("Guest Email: ");
    String email = sc.next();
    while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
        System.out.print("Invalid email format! Please enter a valid email: ");
        email = sc.next();
    }
    
    // Get and validate guest's status
    System.out.print("Guest Status: ");
String status = "";
sc.nextLine(); // Consume leftover newline
while (true) {
    try {
        status = sc.nextLine(); // Read user input
        if (status.matches(".*\\d.*")) { // Status must not contain numbers
            throw new IllegalArgumentException("Status must not contain numbers!");
        }
        break; // Valid status
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        System.out.print("Enter a valid status: ");
    }
}
    // Insert the new guest record into the database
    String sql = "INSERT INTO tbl_guest(g_fname, g_lname, g_age, g_email, g_status) VALUES (?, ?, ?, ?, ?)";
    config conf = new config();
    conf.addRecord(sql, fname, lname, age, email, status);

    System.out.println("Guest added successfully!");
}

// Method to view all guests in the system
public void viewGuest() {
    // Query to fetch all guest records
    String qry = "SELECT * FROM tbl_guest";
    // Headers for displaying the guest table
    String[] hdrs = {"ID", "FName", "LName", "Age", "Email", "Status"};
    // Column mappings from the database
    String[] clmn = {"g_id", "g_fname", "g_lname", "g_age", "g_email", "g_status"};

    config conf = new config();
    conf.viewRecords(qry, hdrs, clmn);
}

// Method to update guest details
public void updateGuest() {
    guest gt = new guest();
    gt.viewGuest(); // Show the list of guests for reference
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    // Validate guest ID
    int id = 0;
    while (true) {
        try {
            System.out.print("Enter Guest ID: ");
            id = sc.nextInt();
            String gstID = "SELECT g_id FROM tbl_guest WHERE g_id = ?";
            if (conf.getSingleValue(gstID, id) != 0) {
                break; // Valid ID
            } else {
                System.out.print("Guest does not exist. Try again: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input! Guest ID must be an integer. Enter again: ");
            sc.next(); // Clear the invalid input
        }
    }
    sc.nextLine();

    // Get and validate new guest first name
    System.out.print("Guest New First Name: ");
    String fname = sc.nextLine();
    while (!fname.matches("[a-zA-Z\\s\\-]+")) { 
        System.out.print("Invalid input! First name must not contain numbers or special characters. Enter again: ");
        fname = sc.nextLine();
    }
    
    // Get and validate new guest last name
    System.out.print("Guest New Last Name: ");
    String lname = sc.nextLine();
    while (!lname.matches("[a-zA-Z\\s\\-]+")) { 
        System.out.print("Invalid input! Last name must not contain numbers or special characters. Enter again: ");
        lname = sc.nextLine();
    }
    
    // Get and validate new age (must be 18+)
    System.out.print("Guest New Age (18+): ");
    int age = 0;
    while (true) {
        try {
            age = sc.nextInt();
            if (age >= 18) {
                break; // Valid age
            } else {
                System.out.print("Invalid age!! Age must be 18 or older. Enter a valid age: ");
            }
        } catch (Exception e) {
            System.out.print("Invalid input!! Enter a valid number for age: ");
            sc.next();
        }
    }

    // Get and validate new email address
    System.out.print("Guest New Email: ");
    String email = sc.next();
    while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
        System.out.print("Invalid email format! Please enter a valid email: ");
        email = sc.next();
    }
    
    // Get and validate new status
    System.out.print("Guest New Status: ");
    String status = "";
    while (true) {
        try {
            status = sc.next(); // Read user input
            if (status.matches(".*\\d.*")) { // Status must not contain numbers
                throw new IllegalArgumentException("Status must not contain numbers!");
            }
            break; // Valid status
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Enter a valid new status: ");
        }
    }

    // Update the guest record in the database
    String qry = "UPDATE tbl_guest SET g_fname = ?, g_lname = ?, g_age = ?, g_email = ?, g_status = ? WHERE g_id = ?";
    conf.updateRecord(qry, fname, lname, age, email, status, id);

    System.out.println("Guest updated successfully!");
}

// Method to delete a guest from the system
public void deleteGuest() {
    Scanner sc = new Scanner(System.in);
    guest gt = new guest();
    gt.viewGuest(); // Display all guests for user reference

    int id = -1; // Initialize with an invalid ID
    boolean validInput = false; // Flag to track valid input

    while (!validInput) {
        try {
            System.out.print("Enter Guest ID: ");
            id = sc.nextInt(); // Try to read an integer

            if (id < 0) { // Optionally validate the ID range
                throw new IllegalArgumentException("Guest ID must be positive.");
            }
            validInput = true; // Input is valid
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            sc.nextLine(); // Clear the input buffer
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid Guest ID.");
            sc.nextLine(); // Clear the input buffer
        }
    }

    // SQL query to delete a guest by ID
    String qry = "DELETE FROM tbl_guest WHERE g_id = ?";

    config conf = new config();
    try {
        conf.deleteRecord(qry, id); // Attempt to delete the record
        System.out.println("Guest deleted successfully!");
    } catch (Exception e) {
        System.out.println("Failed to delete guest: " + e.getMessage());
    }
}
    
}
