package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import static com.pluralsight.HomeScreen.SCANNER;
import static com.pluralsight.HomeScreen.TRANSACTION;

public class Ledger {

    public static ArrayList<Transaction> TRANSACTIONS_ARRAYLIST = TRANSACTION;

    public static void display() throws IOException {

        // Sort in descending order using a custom comparator
        TRANSACTIONS_ARRAYLIST.sort(Collections.reverseOrder(Comparator.comparing(Transaction::getDate)));


        // display method for displaying the ledger menu

        while (true) {
            System.out.println("How can we help you today?");
            System.out.println("\t(A) - All - display all entries");
            System.out.println("\t(D) - Deposits");
            System.out.println("\t(P) - Payments");
            System.out.println("\t(R) - Reports");
            System.out.println("\t(H) - Home");

            System.out.print("Enter a Choice: ");
            String userChoice = SCANNER.nextLine().trim().toUpperCase();

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
                    return; // Exit the loop when returning to the home screen
                default:
                    System.out.println("Invalid choice. Please try again. ");
            }
        }
    }

    public static void displayALL() {
        // Display all transactions in the ledger

        // Iterate through transactions and display all the values
        for (Transaction  t : TRANSACTIONS_ARRAYLIST) {
            System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    public static void displayDeposit() {
        // Display deposits in the ledger

        // Iterate through transactions and display those that match amount > 0
        for(Transaction t : TRANSACTIONS_ARRAYLIST) {
            if(t.getAmount() > 0) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }

    public static void displayPayments() {
        // Display payments in the ledger

        // Iterate through transactions and display those that match amount < 0
        for(Transaction t : TRANSACTIONS_ARRAYLIST) {
            if(t.getAmount() < 0) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(),
                        t.getVendor(), t.getAmount());
            }
        }
    }

    public static void displayReports() throws IOException {

        while (true) {
            // display method for displaying the report menu
            System.out.println("\t(1) - Month To Date");
            System.out.println("\t(2) - Previous Month");
            System.out.println("\t(3) - Year To Date");
            System.out.println("\t(4) - Previous Year");
            System.out.println("\t(5) - Search by Vendor");
            System.out.println("\t(0) - Back to the ledger screen");
            System.out.print("Enter # Choice: ");
            int userInput = SCANNER.nextInt();
            SCANNER.nextLine();


            switch (userInput) {
                case 1:
                    getMonthToDateReport();
                    break;
                case 2:
                    getPreviousMonthReport();
                    break;
                case 3:
                    getYearToDateReport();
                    break;
                case 4:
                    getPreviousYearReport();
                    break;
                case 5:
                    searchByVendorReport();
                    break;
                case 0:
                    display();
                    return;

            }
        }


    }
    private static void getMonthToDateReport() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        System.out.println("Month-to-Date Report");
        System.out.println("*************************");

        // Iterate through transactions and display those in the current month
        for (Transaction t : TRANSACTIONS_ARRAYLIST) {
            LocalDate transactionDate = t.getDate();

            // Check if the transaction occurred in the current month
            if (transactionDate.getYear() == currentDate.getYear() &&
                    transactionDate.getMonth() == currentDate.getMonth()) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }

    private static void getPreviousMonthReport() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the start date of the previous month
        LocalDate startDateOfPreviousMonth = currentDate.minusMonths(1).withDayOfMonth(1);

        // Calculate the end date of the previous month
        LocalDate endDateOfPreviousMonth = currentDate.withDayOfMonth(1).minusDays(1);


        System.out.println("Previous Month Report");
        System.out.println("***************************");

        // Iterate through transactions and display those in the previous month
        for (Transaction t : TRANSACTIONS_ARRAYLIST) {
            LocalDate transactionDate = t.getDate();

            // Check if the transaction occurred in the previous month
            if (transactionDate.isAfter(startDateOfPreviousMonth) && transactionDate.isBefore(endDateOfPreviousMonth)) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }

    private static void getYearToDateReport() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the start date of the current year (January 1 of the current year)
        LocalDate startDateOfYear = LocalDate.of(currentDate.getYear(), Month.JANUARY, 1);


        System.out.println("Year-to-Date Report");
        System.out.println("************************");

        // Iterate through transactions and display those in the current year
        for (Transaction t : TRANSACTIONS_ARRAYLIST) {
            LocalDate transactionDate = t.getDate();

            // Check if the transaction occurred in the current year
            if (transactionDate.isEqual(startDateOfYear) || transactionDate.isAfter(startDateOfYear)) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }

    private static void getPreviousYearReport() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the start date of the previous year (January 1 of the previous year)
        LocalDate startDateOfPreviousYear = LocalDate.of(currentDate.getYear() - 1, Month.JANUARY, 1);

        // Calculate the end date of the previous year (December 31 of the previous year)
        LocalDate endDateOfPreviousYear = LocalDate.of(currentDate.getYear() - 1, Month.DECEMBER, 31);

        System.out.println("Previous Year Report");
        System.out.println("*************************");

        // Iterate through transactions and display those in the previous year
        for (Transaction t : TRANSACTIONS_ARRAYLIST) {
            LocalDate transactionDate = t.getDate();

            // Check if the transaction occurred in the previous year
            if (transactionDate.isEqual(startDateOfPreviousYear) || (transactionDate.isAfter(startDateOfPreviousYear) && transactionDate.isBefore(endDateOfPreviousYear))) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }


    private static void searchByVendorReport() {
        // Prompt the user to enter the vendor they want to search for
        System.out.print("Enter a vendor name to search: ");
        String searchVendor = SCANNER.nextLine();


        System.out.println("Search by Vendor Report");
        System.out.println("*************************");

        // Iterate through transactions and display those that match the vendor
        for (Transaction t : TRANSACTIONS_ARRAYLIST) {
            if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }
}
