package com.ast.MyBills.MainAuxilaries.DModels;

public class DModel_ImportantDates {
    public String Amount;
    public String DueDate;
    public String IssueDate;
    public String ReadingDate;

    public DModel_ImportantDates(){

    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String date) {
        DueDate = date;
    }

    public String getBiller() {
        return Biller;
    }

    public void setBiller(String biller) {
        Biller = biller;
    }

    public DModel_ImportantDates(String date, String amount, String biller) {
        Amount = amount;
        DueDate = date;
        Biller = biller;
    }

    public String Biller;



    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getReadingDate() {
        return ReadingDate;
    }

    public void setReadingDate(String readingDate) {
        ReadingDate = readingDate;
    }
}
