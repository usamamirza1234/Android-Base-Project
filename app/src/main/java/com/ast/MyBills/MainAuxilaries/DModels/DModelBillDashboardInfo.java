package com.ast.MyBills.MainAuxilaries.DModels;

public class DModelBillDashboardInfo {
    public String  BillType;
    public String  Amount;




    public int getStatus() {
        return Status;
    }

    public int getAct() {
        return Act;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setAct(int act) {
        Act = act;
    }

    public int  Status;
    public int  Act;


    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
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

    public String  DueDate;

    public DModelBillDashboardInfo(String billType, String amount, String dueDate, int status, int act) {
        BillType = billType;
        Amount = amount;
        DueDate = dueDate;
        Status = status;
        Act = act;

    }


    public DModelBillDashboardInfo(String billType) {
    }
}
