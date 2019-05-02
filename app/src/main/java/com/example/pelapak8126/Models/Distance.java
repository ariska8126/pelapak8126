package com.example.pelapak8126.Models;

public class Distance {

    String photoPelapak;
    String namaLaundry;
    String idLaundry;
    Float rate;
    String alamat;
    String jarak;
    String statusBuka;

    public Distance() {
    }

    public Distance(String photoPelapak, String namaLaundry, String idLaundry,
                    Float rate, String alamat) {
        this.photoPelapak = photoPelapak;
        this.namaLaundry = namaLaundry;
        this.idLaundry = idLaundry;
        this.rate = rate;
        this.alamat = alamat;
    }

    public String getStatusBuka() {
        return statusBuka;
    }

    public void setStatusBuka(String statusBuka) {
        this.statusBuka = statusBuka;
    }

    public String getPhotoPelapak() {
        return photoPelapak;
    }

    public void setPhotoPelapak(String photoPelapak) {
        this.photoPelapak = photoPelapak;
    }

    public String getNamaLaundry() {
        return namaLaundry;
    }

    public void setNamaLaundry(String namaLaundry) {
        this.namaLaundry = namaLaundry;
    }

    public String getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(String idLaundry) {
        this.idLaundry = idLaundry;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
