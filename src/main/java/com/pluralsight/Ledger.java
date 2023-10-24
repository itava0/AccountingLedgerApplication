package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static com.pluralsight.HomeScreen.SCANNER;
import static com.pluralsight.HomeScreen.TRANSACTION;

public class Ledger {

    public static void display() throws IOException {

        // display method for displaying the ledger menu

        while (true) {
            System.out.println("How can we help you today?");
            System.out.println("\t(A) - All - display all entries");
            System.out.println("\t(D) - Deposits");
            System.out.println("\t(P) - Payments");
            System.out.println("\t(R) - Reports");
            System.out.println("\t(H) - Home");

            System.out.print("Enter a Choice: ");
            String userChoice = SCANNER.next().trim().toUpperCase();

            switch (userChoice) {
                case "A":
                    displayALL();
                    break;
                case "D":
                    displayDeposit();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    displayReports();
                    break;
                case "H":
                    HomeScreen.display();
                    break; // Exit the loop when returning to the home screen
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displayALL() {
        // Display all transactions in the ledger

        // Iterate through transactions and display all the values
        for (Transaction  t : TRANSACTION.values()) {
            System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    public static void displayDeposit() {
        // Display deposits in the ledger

        // Iterate through transactions and display those that match amount > 0
        for(Transaction t : TRANSACTION.values()) {
            if(t.amount > 0) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }


}
