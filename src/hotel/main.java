package hotel;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // Create a Scanner object to take user input
        Scanner sc = new Scanner(System.in);
        boolean exit = false; // Flag to control the loop for the main menu

        // Main menu loop
        while (!exit) {
            // Display the main menu options
            System.out.println("\n==========================");
            System.out.println("||      Main Menu       ||");
            System.out.println("==========================");
            System.out.println("||      1. Room         ||");
            System.out.println("||      2. Guest        ||");
            System.out.println("||      3. Bookings     ||");
            System.out.println("||      4. Report       ||");
            System.out.println("||      5. Admin        ||");
            System.out.println("||      6. Exit         ||");
            System.out.println("==========================\n");

            int action = 0; // Variable to store the user's choice
            boolean validInput = false; // Flag to ensure valid input

            // Input validation loop
            while (!validInput) {
                try {
                    System.out.print("Enter action: ");
                    action = sc.nextInt(); // Read user input

                    // Validate if the input is within the range of menu options
                    if (action < 1 || action > 6) {
                        throw new IllegalArgumentException("Invalid choice. Please choose between 1 and 6.");
                    }
                    validInput = true; // Input is valid
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); // Display error message for invalid range
                    sc.nextLine(); // Clear the input buffer
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number."); // Handle non-integer input
                    sc.nextLine(); // Clear the input buffer
                }
            }

            // Process the user's choice
            switch (action) {
                case 1:                  
                    room rm = new room();
                    rm.aRoom();
                    break;
                case 2:                   
                    guest gt = new guest();
                    gt.aGuest();
                    break;
                case 3: 
                    bookings bk = new bookings();
                    bk.aBookings();
                    break;
                case 4:
                    report rp = new report();
                    rp.aReport();
                    break;
                case 5:
                    admin adm = new admin();
                    adm.editStatus();
                    break;
                case 6:
                    // Exit the program
                    exit = true;
                    System.out.println("Thank you, See you!!");
                    break;
                default:
                    // Handle unexpected cases (not reachable due to validation)
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
