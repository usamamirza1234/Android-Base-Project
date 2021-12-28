package com.ast.MyBills.MainAuxilaries.DModels;


public class DModel_Bills {


    public DModel_Bills() {
    }

  public   String BillType;
  public   String Refference_number;
 public   String Account_number;

    public DModel_Bills(String billType, String refference_number, String account_number) {
        BillType = billType;
        Refference_number = refference_number;
        Account_number = account_number;
    }
}

