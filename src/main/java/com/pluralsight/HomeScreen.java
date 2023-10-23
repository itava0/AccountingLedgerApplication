package com.pluralsight;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class HomeScreen {

    public static Scanner SCANNER = new Scanner(System.in);

    public static HashMap<String, Transaction> TRANSACTION = new HashMap<>();

    public static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static DecimalFormat df = new DecimalFormat("0.00");

    public static void display() throws IOException {
        readTransactions();

        // Display the products in the inventory and provide options for users to interact with them
      /*  System.out.println("We carry the following inventory: ");
        for (Transaction  trans : TRANSACTION.values()) {
            System.out.printf("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount $%.2f\n",
                    trans.getDate(), trans.getTime(), trans.getDescription(), trans.getVendor(), trans.getAmount());
        }*/


        System.out.println("Welcome to Chase");
        System.out.println("How can we help you today?");
        System.out.println("\t(D)-Add Deposit");
        System.out.println("\t(P)-Make a Payment (Debit)");
        System.out.println("\t(L)-Ledger");
        System.out.println("\t(X)-Exit");

        System.out.print("Enter # Choice: ");
        String userChoice = SCANNER.nextLine().trim().toUpperCase();


        switch (userChoice) {
            case "D":
                addDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                    displayALL();
                break;
            case "X":

                break;
        }

    }

    public static void readTransactions() throws IOException {
        // Read product information from a CSV file and populate the inventory
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
                    TRANSACTION.put(transactionDes, new Transaction(transactionDate, transactionTime, transactionDes, transactionVendor, transactionAmount));
                }
            }
        }

        // Close the file outside the loop
        readFile.close();
    }

    public static void addDeposit() throws IOException {
        System.out.print("What's the name of the deposit?  ");
        String description = SCANNER.nextLine().trim();

        System.out.print("What's the name of the vendor? ");
        String vendor = SCANNER.nextLine().trim();

        System.out.print("What's the amount? ");
        double amount = SCANNER.nextDouble();
        String amountFormatted = df.format(amount);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

        String date = String.valueOf(LocalDate.now());
        String time = fmt.format(LocalTime.now());

        bufferedWriter.write( date + "|" + time + "|"  + description + "|" + vendor + "|" + amountFormatted +"\n");

        bufferedWriter.close();
    }

    public static void makePayment() throws IOException {
        System.out.print("What's name of the item your purchasing? ");
        String description = SCANNER.nextLine().trim();

        System.out.print("What's the name of the vendor/company? ");
        String vendor = SCANNER.nextLine().trim();

        System.out.print("What's the amount? ");
        double amount = SCANNER.nextDouble();
        amount *= -1;
        String amountFormatted = df.format(amount);


        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

        String date = String.valueOf(LocalDate.now());
        String time = fmt.format(LocalTime.now());

        bufferedWriter.write( date + "|" + time + "|"  + description + "|" + vendor + "|" + amountFormatted  +"\n");

        bufferedWriter.close();
    }

    public static void displayALL() {
        for(Transaction t : TRANSACTION.values()) {
            if(t.amount > 0) {
                System.out.println(t);
            }
        }
    }

}
