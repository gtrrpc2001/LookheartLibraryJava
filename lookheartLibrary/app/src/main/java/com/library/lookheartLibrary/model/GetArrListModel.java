package com.library.lookheartLibrary.model;

public class GetArrListModel {

    private String writetime;
    private String address; // !null == emergency

    // Getter
    // Setter
    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
