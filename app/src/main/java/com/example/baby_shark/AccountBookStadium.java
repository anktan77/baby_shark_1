package com.example.baby_shark;

public class AccountBookStadium {
    private String name;
    private String email;
    private String phone;

    AccountBookStadium(String hovaten, String email, String phone){}

    public AccountBookStadium(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}