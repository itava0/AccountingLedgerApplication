package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;


import static com.pluralsight.HomeScreen.SCANNER;
import static com.pluralsight.HomeScreen.TRANSACTION;

public class LedgerScreen {

    public static void display() throws IOException {

        //read transactions file to shows new entries
        HomeScreen.readTransactions();

        // Sort in descending order using a custom comparator by comparing the LocalDateTime of each object
        TRANSACTION.sort(Collections.reverseOrder(Comparator.comparing(Transaction::getDateTime)));

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
        for (Transaction  t : TRANSACTION) {
            System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    public static void displayDeposit() {
        // Display deposits in the ledger

        // Iterate through transactions and display those that match amount > 0
        for(Transaction t : TRANSACTION) {
            if(t.getAmount() > 0) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }

    public static void displayPayments() {
        // Display payments in the ledger

        // Iterate through transactions and display those that match amount < 0
        for(Transaction t : TRANSACTION) {
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
            System.out.println("\t(6) - Custom Search");
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
                case 6:
                    customSearch();
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
        for (Transaction t : TRANSACTION) {
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
        for (Transaction t : TRANSACTION) {
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
        for (Transaction t : TRANSACTION) {
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
        for (Transaction t : TRANSACTION) {
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
        for (Transaction t : TRANSACTION) {
            if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                // Display transaction details
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f%n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }
    public static void customSearch() {
        System.out.println("Custom Search - Enter search criteria (leave empty to skip):");
        System.out.print("Start Date (yyyy-MM-dd): ");
        String startDateStr = SCANNER.nextLine().trim();
        System.out.print("End Date (yyyy-MM-dd): ");
        String endDateStr = SCANNER.nextLine().trim();
        System.out.print("Description: ");
        String description = SCANNER.nextLine().trim();
        System.out.print("Vendor: ");
        String vendor = SCANNER.nextLine().trim();
        System.out.print("Amount: ");
        String amountStr = SCANNER.nextLine().trim();

        // Parse user input for date and amount
        LocalDate startDate = startDateStr.isEmpty() ? null : LocalDate.parse(startDateStr);
        LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr);
        double amount = amountStr.isEmpty() ? Double.NaN : Double.parseDouble(amountStr);

        // Filter and display ledger entries based on user input
        filterAndDisplay(startDate, endDate, description, vendor, amount);
    }

    public static void filterAndDisplay(LocalDate startDate, LocalDate endDate, String description, String vendor, double amount) {
        System.out.println("Custom Search Results:");
        for (Transaction t : TRANSACTION) {
            // Check if the transaction matches the user-specified criteria.

            // Check if the transaction date is within the specified date range, if provided.
            boolean matchCriteria = startDate == null || endDate == null || (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate));

            // If a transaction does not match the specified description, set the matchCriteria flag to false.
            if (!description.isEmpty() && !t.getDescription().equalsIgnoreCase(description)) {
                matchCriteria = false;
            }

            // If a transaction does not match the specified vendor, set the matchCriteria flag to false.
            if (!vendor.isEmpty() && !t.getVendor().equalsIgnoreCase(vendor)) {
                matchCriteria = false;
            }

            // If a transaction does not match the specified amount, set the matchCriteria flag to false.
            if (!Double.isNaN(amount) && t.getAmount() != amount) {
                matchCriteria = false;
            }

            // Display the transaction as a search result only if it meets all specified search criteria.
            // Transactions that do not meet the criteria are excluded from the results.
            if (matchCriteria) {
                System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                        t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }
}
