package com.example.baby_shark.Account.OOP;

public class RecycleViewTimeBook {
    private String timeS;
    private String timeE;

    public RecycleViewTimeBook(){}

    public RecycleViewTimeBook(String timeS, String timeE) {
        this.timeS = timeS;
        this.timeE = timeE;
    }

    public String getTimeS() {
        return timeS;
    }

    public void setTimeS(String timeS) {
        this.timeS = timeS;
    }

    public String getTimeE() {
        return timeE;
    }

    public void setTimeE(String timeE) {
        this.timeE = timeE;
    }
}
