package com.example.baby_shark;

public class InforBookStadium {
    private String dayPlay;
    private String timeStart;
    private String timeEnd;
    private String namePlay;
    private String phonePlay;
    private String describePlay;

    public InforBookStadium(String dayPlay, String timeStart, String timeEnd, String namePlay, String phonePlay, String describePlay) {
        this.dayPlay = dayPlay;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.namePlay = namePlay;
        this.phonePlay = phonePlay;
        this.describePlay = describePlay;
    }

    public String getDayPlay() {
        return dayPlay;
    }

    public void setDayPlay(String dayPlay) {
        this.dayPlay = dayPlay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getNamePlay() {
        return namePlay;
    }

    public void setNamePlay(String namePlay) {
        this.namePlay = namePlay;
    }

    public String getPhonePlay() {
        return phonePlay;
    }

    public void setPhonePlay(String phonePlay) {
        this.phonePlay = phonePlay;
    }

    public String getDescribePlay() {
        return describePlay;
    }

    public void setDescribePlay(String describePlay) {
        this.describePlay = describePlay;
    }
}
