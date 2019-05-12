package com.example.pelapak8126.Models;

public class NewChatList {

    private String guestId;
    private String laundryId;
    private String guestPhoto;
    private String namaGuest;
    private String timeStamp;
    private String msg;

    public NewChatList(String guestId, String laundryId, String guestPhoto,
                       String namaGuest, String msg) {
        this.guestId = guestId;
        this.laundryId = laundryId;
        this.guestPhoto = guestPhoto;
        this.namaGuest = namaGuest;
        this.msg = msg;
    }

    public NewChatList() {
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(String laundryId) {
        this.laundryId = laundryId;
    }

    public String getGuestPhoto() {
        return guestPhoto;
    }

    public void setGuestPhoto(String guestPhoto) {
        this.guestPhoto = guestPhoto;
    }

    public String getNamaGuest() {
        return namaGuest;
    }

    public void setNamaGuest(String namaGuest) {
        this.namaGuest = namaGuest;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
