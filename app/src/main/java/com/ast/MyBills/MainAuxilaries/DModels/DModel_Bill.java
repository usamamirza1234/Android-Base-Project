package com.ast.MyBills.MainAuxilaries.DModels;

public class DModel_Bill {
    String MONTH;
    String UNITS;
    String BILL;
    String PAYMENT;


    public DModel_Bill(String MONTH, String UNITS, String BILL, String PAYMENT) {
        this.MONTH = MONTH;
        this.UNITS = UNITS;
        this.BILL = BILL;
        this.PAYMENT = PAYMENT;
    }


    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getBILL() {
        return BILL;
    }

    public void setBILL(String BILL) {
        this.BILL = BILL;
    }

    public String getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(String PAYMENT) {
        this.PAYMENT = PAYMENT;
    }
}
