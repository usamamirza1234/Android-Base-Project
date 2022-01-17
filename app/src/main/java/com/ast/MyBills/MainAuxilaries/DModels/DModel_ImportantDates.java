package com.ast.MyBills.MainAuxilaries.DModels;

public class DModel_ImportantDates {
    public String Amount;
    public String Date;

    public DModel_ImportantDates(){

    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBiller() {
        return Biller;
    }

    public void setBiller(String biller) {
        Biller = biller;
    }

    public DModel_ImportantDates(String date, String amount, String biller) {
        Amount = amount;
        Date = date;
        Biller = biller;
    }

    public String Biller;
}
