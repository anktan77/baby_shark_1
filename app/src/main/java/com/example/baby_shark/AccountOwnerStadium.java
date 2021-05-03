package com.example.baby_shark;

public class AccountOwnerStadium {
    private String name;
    private String email;
    private String phone;
    private String picture;
    private String address;

    public  AccountOwnerStadium(){}
    public AccountOwnerStadium(String name, String email, String phone, String picture, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.address = address;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
