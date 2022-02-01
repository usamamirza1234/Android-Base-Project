package com.ast.MyBills.MainAuxilaries.DModels;

public class DModel_CreateAccount {
    String UserName;
    String Email;
    String Password;
    String Phone;


    public DModel_CreateAccount(String UserName, String Email, String Password, String Phone) {
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
        this.Phone = Phone;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}
