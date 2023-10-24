package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDate date;

    private LocalTime time;

    private String description;

    private String vendor;

    private Double amount;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String transactionDes, String transactionVendor, double transactionAmount) {
        this.date = transactionDate;
        this.time = transactionTime;
        this.description = transactionDes;
        this.vendor = transactionVendor;
        this.amount = transactionAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time.format(fmt);
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.date + "|" + this.time + "|" + this.description + "|" + this.vendor + "|" + this.amount;
    }

}
