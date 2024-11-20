package hotel;

import java.util.Scanner;

public class admin {

    public void editStatus() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n------------------");
            System.out.println("| Admin Interface |");
            System.out.println("------------------\n");
            System.out.println("1. Edit Status");
            System.out.println("2. View Status");
            System.out.println("3. Main Menu");

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

            room rm = new room();

            switch (action) {
                case 1:
                    rm.addRoom();
                    break;
                case 2:
                    rm.viewRoom();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you, See you!!");
    }
    
    
    
}