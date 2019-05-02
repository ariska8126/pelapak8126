package com.example.pelapak8126.Models;

public class OwnerLaundry {

    private String ownerKey;
    private String userId;
    private String namaLaundry;
    private String alamat;
    private String phone;
    private double latitude;
    private double longitude;
    private Float rate;
    private String statusJemput;
    private String statusBuka;
    private Object timeStamp;
    private String ownerEmail;
    private String ownerName;
    private String ownerPhoto;
    private String laundryPhoto;

    public OwnerLaundry() {
    }

    public OwnerLaundry(String userId, String namaLaundry, String alamat, String phone,
                        double latitude, double longitude, Float rate,
                        String statusJemput, String statusBuka, String ownerEmail,
                        String ownerName, String ownerPhoto, String laundryPhoto) {
        this.userId = userId;
        this.namaLaundry = namaLaundry;
        this.alamat = alamat;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.statusJemput = statusJemput;
        this.statusBuka = statusBuka;
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.ownerPhoto = ownerPhoto;
        this.laundryPhoto = laundryPhoto;
    }

    public String getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNamaLaundry() {
        return namaLaundry;
    }

    public void setNamaLaundry(String namaLaundry) {
        this.namaLaundry = namaLaundry;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getStatusJemput() {
        return statusJemput;
    }

    public void setStatusJemput(String statusJemput) {
        this.statusJemput = statusJemput;
    }

    public String getStatusBuka() {
        return statusBuka;
    }

    public void setStatusBuka(String statusBuka) {
        this.statusBuka = statusBuka;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhoto() {
        return ownerPhoto;
    }

    public void setOwnerPhoto(String ownerPhoto) {
        this.ownerPhoto = ownerPhoto;
    }

    public String getLaundryPhoto() {
        return laundryPhoto;
    }

    public void setLaundryPhoto(String laundryPhoto) {
        this.laundryPhoto = laundryPhoto;
    }
}
