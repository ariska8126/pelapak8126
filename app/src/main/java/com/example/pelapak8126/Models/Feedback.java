package com.example.pelapak8126.Models;

public class Feedback {

    private String uIdLaundry;
    private String uIdGuest;
    private String namaGuest;
    private String rate;
    private String comment;
    private String guestPhotoUrl;
    private String feedBackKey;
    private Object timeStamp;

    public Feedback(String uIdLaundry, String uIdGuest, String namaGuest,
                    String rate, String comment, String guestPhotoUrl) {
        this.uIdLaundry = uIdLaundry;
        this.uIdGuest = uIdGuest;
        this.namaGuest = namaGuest;
        this.rate = rate;
        this.comment = comment;
        this.guestPhotoUrl = guestPhotoUrl;
    }

    public Feedback() {
    }

    public String getuIdLaundry() {
        return uIdLaundry;
    }

    public void setuIdLaundry(String uIdLaundry) {
        this.uIdLaundry = uIdLaundry;
    }

    public String getuIdGuest() {
        return uIdGuest;
    }

    public void setuIdGuest(String uIdGuest) {
        this.uIdGuest = uIdGuest;
    }

    public String getNamaGuest() {
        return namaGuest;
    }

    public void setNamaGuest(String namaGuest) {
        this.namaGuest = namaGuest;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGuestPhotoUrl() {
        return guestPhotoUrl;
    }

    public void setGuestPhotoUrl(String guestPhotoUrl) {
        this.guestPhotoUrl = guestPhotoUrl;
    }

    public String getFeedBackKey() {
        return feedBackKey;
    }

    public void setFeedBackKey(String feedBackKey) {
        this.feedBackKey = feedBackKey;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
