package hotel;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String response;
        boolean exit = false;

        do {
            System.out.println("\n------------------------");
            System.out.println("| Hotel Booking System |");
            System.out.println("------------------------\n");
            System.out.println("1. Room");
            System.out.println("2. Guest");
            System.out.println("3. Bookings");
            System.out.println("4. Exit");

            int action = 0; 
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Enter action: ");
                    action = sc.nextInt();
                    if (action < 1 || action > 4) {
                        throw new IllegalArgumentException("Invalid choice. Please choose between 1 and 4.");
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
                    exit = true;
                    break;
            }

                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank you, See you!!");
    }
}