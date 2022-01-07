package com.ast.MyBills.MainAuxilaries.DModels;

public class DModelBillAnaylsis {
    public String Payment;
    public String Month;
    public String Units;

    public DModelBillAnaylsis(String payment, String month, String units) {
        Payment = payment;
        Month = month;
        Units = units;
    }
    public DModelBillAnaylsis() {

    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }
}
