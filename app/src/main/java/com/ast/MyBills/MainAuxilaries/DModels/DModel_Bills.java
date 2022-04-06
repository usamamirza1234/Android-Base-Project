package com.ast.MyBills.MainAuxilaries.DModels;


public class DModel_Bills {


    public String BillType;
    public String Refference_number;
    public String Account_number;
    public String payableafterduedate;
    public String duedate;
    public String Biller;
    public Boolean Paid;
    //
    public String Amount;
    public String DueDate;
    public String IssueDate;
    public String ReadingDate;
    public String Billerr;

    public DModel_Bills() {

    }

    //
    public DModel_Bills(String s, String s1, String s2, String payableafterduedate, String duedate, String biller) {
        BillType = s;
        Refference_number = s1;
        Account_number = s2;
        this.payableafterduedate = payableafterduedate;
        this.duedate = duedate;
        this.Biller = biller;
        this.Paid = false;


    }

    public String getBiller() {
        return Biller;
    }

    public void setBiller(String biller) {
        Biller = biller;
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

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getReadingDate() {
        return ReadingDate;
    }

    public void setReadingDate(String readingDate) {
        ReadingDate = readingDate;
    }

    public String getBillerr() {
        return Billerr;
    }

    public void setBillerr(String billerr) {
        Billerr = billerr;
    }

    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    public String getRefference_number() {
        return Refference_number;
    }

    public void setRefference_number(String refference_number) {
        Refference_number = refference_number;
    }

    public String getAccount_number() {
        return Account_number;
    }

    public void setAccount_number(String account_number) {
        Account_number = account_number;
    }

    public String getPayableafterduedate() {
        return payableafterduedate;
    }

    public void setPayableafterduedate(String payableafterduedate) {
        this.payableafterduedate = payableafterduedate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public int getAct() {
        return 0;
    }

    public Boolean getPaid() {
        return Paid;
    }

    public void setPaid(Boolean paid) {
        Paid = paid;
    }
}

