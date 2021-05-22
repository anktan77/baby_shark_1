package com.example.baby_shark.OOP;

public class InForResponseStadium {
    private String nameStadium;
    private String dayPlay;
    private String describePlay;
    private String timeS;
    private String timeE;
    private String picture;
    public InForResponseStadium(){}
    public InForResponseStadium(String nameStadium, String dayPlay, String describePlay, String timeS, String timeE, String picture) {
        this.nameStadium = nameStadium;
        this.dayPlay = dayPlay;
        this.describePlay = describePlay;
        this.timeS = timeS;
        this.timeE = timeE;
        this.picture = picture;
    }

    public String getNameStadium() {
        return nameStadium;
    }

    public void setNameStadium(String nameStadium) {
        this.nameStadium = nameStadium;
    }

    public String getDayPlay() {
        return dayPlay;
    }

    public void setDayPlay(String dayPlay) {
        this.dayPlay = dayPlay;
    }

    public String getDescribePlay() {
        return describePlay;
    }

    public void setDescribePlay(String describePlay) {
        this.describePlay = describePlay;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
