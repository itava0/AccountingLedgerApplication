package com.pluralsight;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeScreen {

    public static Scanner SCANNER = new Scanner(System.in);

    public static ArrayList<Transaction> TRANSACTION = new ArrayList<>();

    public static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static DecimalFormat DF = new DecimalFormat("0.00");

    public static void display() throws IOException {
        // This method represents the main screen for the application.
        // It allows the user to perform different actions.

        // First, it reads transactions from a CSV file to populate the TRANSACTION HashMap.
        readTransactions();

        while (true) {
            System.out.println("Welcome to Chase");
            System.out.println("How can we help you today?");
            System.out.println("\t(D) - Add Deposit");
            System.out.println("\t(P) - Make a Payment (Debit)");
            System.out.println("\t(L) - Ledger");
            System.out.println("\t(X) - Exit");

            System.out.print("Enter a Choice: ");
            String userChoice = SCANNER.nextLine().trim().toUpperCase();

            switch (userChoice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    LedgerScreen.display();
                    break;
                case "X":
                    SCANNER.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void readTransactions() throws IOException {
        // This method reads transactions from a CSV file and populates the TRANSACTION HashMap.
        // It uses a BufferedReader to read data line by line from the CSV file.

        TRANSACTION.clear();// Removes all values from it

        BufferedReader readFile = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
        String input;
        LocalDate transactionDate;
        LocalTime transactionTime;
        String transactionDes;
        String transactionVendor;
        double transactionAmount;

        while ((input = readFile.readLine()) != null) {
            String[] transactionList = input.split("\\|");
            if (!transactionList[0].equals("date")) {
                transactionDate = LocalDate.parse(transactionList[0]);
                transactionTime = LocalTime.parse(transactionList[1]);
                transactionDes = transactionList[2];
                transactionVendor = transactionList[3];

                // Check for empty or non-numeric amount field
                if (!transactionList[4].isEmpty()) {
                    transactionAmount = Double.parseDouble(transactionList[4]);
                    TRANSACTION.add(new Transaction(transactionDate, transactionTime, transactionDes, transactionVendor, transactionAmount));
                }
            }
        }

        // Close the file outside the loop
        readFile.close();
    }

    public static void addDeposit() throws IOException {
        // This method allows the user to add a deposit entry to a CSV file.
        // It collects information like description, vendor, and amount from the user.

        while (true) {
            System.out.print("What's the name of the deposit?  ");
            String description = SCANNER.nextLine().trim();

            System.out.print("What's the name of the vendor? ");
            String vendor = SCANNER.nextLine().trim();

            System.out.print("What's the amount?");

            // Check if the user entered a valid double (numeric) value.
            if (!SCANNER.hasNextDouble()) {
                System.out.println("You did not enter a number.");
                SCANNER.nextLine();
                addDeposit(); // Recursively call the method to retry input.
            }
            double amount = SCANNER.nextDouble();
            SCANNER.nextLine(); // Consume the newline character.

            String amountFormatted = DF.format(amount);

            // Check if either description or vendor fields are empty.
            if (description.isEmpty() || vendor.isEmpty()) {
                System.out.println("Please, answer all the prompts ");
                addDeposit(); // Recursively call the method to retry input.
            }

            // Open a BufferedWriter to append to the transactions.csv file.
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            // Get the current date and time.
            String date = String.valueOf(LocalDate.now());
            String time = FMT.format(LocalTime.now());

            // Write the deposit entry to the CSV file in the specified format.
            bufferedWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amountFormatted + "\n");

            System.out.println("Deposit has been recorded! \n Would you like to enter another deposit? Yes or No? ");
            String userInput = SCANNER.nextLine().trim();

            // Check if the user wants to enter another deposit.
            if (userInput.equalsIgnoreCase("no")) {
                bufferedWriter.close(); // Close the BufferedWriter.
                return;
            }

            bufferedWriter.close(); // Close the BufferedWriter.
        }
    }


    public static void makePayment() throws IOException {
        // This method allows the user to make a payment entry to the CSV file.
        // It collects information like description, vendor, and amount (negative for payment) from the user.

        while (true) {
            System.out.print("What's the name of the deposit?  ");
            String description = SCANNER.nextLine().trim();

            System.out.print("What's the name of the vendor? ");
            String vendor = SCANNER.nextLine().trim();

            System.out.print("What's the amount?");

            // Check if the user entered a valid number value.
            if (!SCANNER.hasNextDouble()) {
                System.out.println("You did not enter a number. ");
                SCANNER.nextLine();
                addDeposit(); // Recursively call the method to retry input.
            }
            double amount = SCANNER.nextDouble();
            amount *= -1; //converts amount into a negative amount since it is a payment
            SCANNER.nextLine(); // Consume the newline character.

            String amountFormatted = DF.format(amount);

            // Check if either description or vendor fields are empty.
            if (description.isEmpty() || vendor.isEmpty()) {
                System.out.println("Please, answer all the prompts ");
                makePayment(); // Recursively call the method to retry input.
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            String date = String.valueOf(LocalDate.now());
            String time = FMT.format(LocalTime.now());

            // Write the payment entry to the CSV file
            bufferedWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amountFormatted + "\n");

            System.out.println("Payment has been recorded! \n Would you like to enter another Payment? Yes or No? ");
            String userInput = SCANNER.nextLine().trim();

            // Check if the user wants to enter another payment.
            if (userInput.equalsIgnoreCase("no")) {
                bufferedWriter.close(); // Close the BufferedWriter.
                return;
            }

            bufferedWriter.close(); // Close the BufferedWriter.
        }
    }
}

