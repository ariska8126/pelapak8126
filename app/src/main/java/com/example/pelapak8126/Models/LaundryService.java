package com.example.pelapak8126.Models;


public class LaundryService {

    private String namaLayanan;
    private String deskripsi;
    private String biayaPerkilo;
    private Object timeStamp;
    private String serviceKey;
    private String userId;
    private String userPhoto;

    public LaundryService(String namaLayanan, String deskripsi, String biayaPerkilo,
                          String userId, String userPhoto) {
        this.namaLayanan = namaLayanan;
        this.deskripsi = deskripsi;
        this.biayaPerkilo = biayaPerkilo;
        this.userId = userId;
        this.userPhoto = userPhoto;
    }

    public LaundryService() {
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getBiayaPerkilo() {
        return biayaPerkilo;
    }

    public void setBiayaPerkilo(String biayaPerkilo) {
        this.biayaPerkilo = biayaPerkilo;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
