package com.ast.MyBills.MainAuxilaries.DModels;


public class DModel_Bills {


    public String BillType;
    public String Refference_number;
    public String Account_number;
    public String payableafterduedate;
    public String duedate;
    public DModel_Bills(String s, String s1, String s2, String payableafterduedate, String duedate) {
        BillType = s;
        Refference_number = s1;
        Account_number = s2;
        payableafterduedate = payableafterduedate;
        duedate = duedate;
    }

    public DModel_Bills(String billType, String refference_number, String account_number) {
        BillType = billType;
        Refference_number = refference_number;
        Account_number = account_number;
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
}

