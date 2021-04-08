package com.example.baby_shark;

public class BookStadium {
    private String time;
    private String name;
    private String phone;
    private String describe;

    public BookStadium(String time, String name, String phone, String describe) {
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.describe = describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
