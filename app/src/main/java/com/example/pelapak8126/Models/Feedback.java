package com.example.pelapak8126.Models;

public class Feedback {

    String idLaundry;
    String layanan;
    Float rate;
    String komentar;
    String transkey;
    String idGuest;
    String photoGuest;
    String namaGuest;
    Object timeStamp;
    String feebackKey;

    public Feedback(String idLaundry, String layanan, Float rate, String komentar,
                    String transkey, String idGuest, String photoGuest, String namaGuest) {

        this.idLaundry = idLaundry;
        this.layanan = layanan;
        this.rate = rate;
        this.komentar = komentar;
        this.transkey = transkey;
        this.idGuest = idGuest;
        this.photoGuest = photoGuest;
        this.namaGuest = namaGuest;
    }

    public Feedback() {
    }

    public String getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(String idLaundry) {
        this.idLaundry = idLaundry;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getTranskey() {
        return transkey;
    }

    public void setTranskey(String transkey) {
        this.transkey = transkey;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getPhotoGuest() {
        return photoGuest;
    }

    public void setPhotoGuest(String photoGuest) {
        this.photoGuest = photoGuest;
    }

    public String getNamaGuest() {
        return namaGuest;
    }

    public void setNamaGuest(String namaGuest) {
        this.namaGuest = namaGuest;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFeebackKey() {
        return feebackKey;
    }

    public void setFeebackKey(String feebackKey) {
        this.feebackKey = feebackKey;
    }

}
