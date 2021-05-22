package com.example.baby_shark.OOP;

public class ListFindStadium {
    private String nameStadium;
    private String addressStadium;
    private String pictureStadium;

    public ListFindStadium(){}

    public ListFindStadium(String nameStadium, String addressStadium, String pictureStadium) {
        this.nameStadium = nameStadium;
        this.addressStadium = addressStadium;
        this.pictureStadium = pictureStadium;
    }

    public String getNameStadium() {
        return nameStadium;
    }

    public void setNameStadium(String nameStadium) {
        this.nameStadium = nameStadium;
    }

    public String getAddressStadium() {
        return addressStadium;
    }

    public void setAddressStadium(String addressStadium) {
        this.addressStadium = addressStadium;
    }

    public String getPictureStadium() {
        return pictureStadium;
    }

    public void setPictureStadium(String pictureStadium) {
        this.pictureStadium = pictureStadium;
    }
}
