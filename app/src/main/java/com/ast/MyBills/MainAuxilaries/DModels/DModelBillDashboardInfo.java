package com.ast.MyBills.MainAuxilaries.DModels;

public class DModelBillDashboardInfo {
    public String  BillType;
    public String  Amount;

    public String getStatus() {
        return Status;
    }

    public String getAct() {
        return Act;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setAct(String act) {
        Act = act;
    }

    public String  Status;
    public String  Act;


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

    public DModelBillDashboardInfo(String billType, String amount, String dueDate, String status, String act) {
        BillType = billType;
        Amount = amount;
        DueDate = dueDate;
        Status = status;
        Act = act;

    }

    public DModelBillDashboardInfo() {
    }
}
