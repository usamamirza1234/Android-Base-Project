package com.ast.MyBills.MainAuxilaries.DModels;

public class DModel_EditProfile {
    String UserName;
    String Email;

    String Phone;


    public DModel_EditProfile(String UserName, String Email, String Phone) {
        this.UserName = UserName;
        this.Email = Email;

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


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}
