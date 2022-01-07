package com.ast.MyBills.MainAuxilaries.DModels;

public class DModelBillInfo {
    public String  BillType;
    public String  City;
    public String  BillMonth;
    public String  ConsumerID;
    public String  DueDate;
    public String  IusseDate;
    public String  AfterDueDate;
    public String  WithinDueDate;
    public String  ReadingDate;

    public String getBillMonth() {
        return BillMonth;
    }

    public void setBillMonth(String billMonth) {
        BillMonth = billMonth;
    }

    public String getConsumerID() {
        return ConsumerID;
    }

    public void setConsumerID(String consumerID) {
        ConsumerID = consumerID;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getIusseDate() {
        return IusseDate;
    }

    public void setIusseDate(String iusseDate) {
        IusseDate = iusseDate;
    }

    public String getAfterDueDate() {
        return AfterDueDate;
    }

    public void setAfterDueDate(String afterDueDate) {
        AfterDueDate = afterDueDate;
    }

    public String getWithinDueDate() {
        return WithinDueDate;
    }

    public void setWithinDueDate(String withinDueDate) {
        WithinDueDate = withinDueDate;
    }

    public String getReadingDate() {
        return ReadingDate;
    }

    public void setReadingDate(String readingDate) {
        ReadingDate = readingDate;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String  Reference;


    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String  Address;

    public DModelBillInfo(String billType, String city, String address) {
        BillType = billType;
        City = city;
        Address = address;
    }

    public DModelBillInfo() {
    }
}
