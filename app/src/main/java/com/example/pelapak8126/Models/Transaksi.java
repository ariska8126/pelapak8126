package com.example.pelapak8126.Models;

public class Transaksi {

    String namaPelapak;
    String photoGuest;
    String longitudeLaundry;
    String latitudeLaundry;
    String longitudeGuest;
    String latitudeGuest;
    String namaGuest;
    String idGuest;
    String idLaundry;
    String setrika;
    String antarjemput;
    String deskripsi;
    String layanan;
    String transKey;
    String photoPelapak;
    String alamatPelapak;
    String namaLaundry;
    String timeStamp;
    String berat;
    String biaya;
    String proses;
    String statusBayar;
    String note;

    public void setNote(String note) {
        this.note = note;
    }

    public Transaksi() {
    }

    public Transaksi(String namaPelapak, String photoGuest, String longitudeLaundry,
                     String latitudeLaundry, String longitudeGuest, String latitudeGuest,
                     String namaGuest, String idGuest, String idLaundry, String setrika,
                     String antarjemput, String deskripsi, String layanan, String transKey,
                     String photoPelapak, String alamatPelapak, String namaLaundry,
                     String timeStamp, String proses, String berat, String biaya,
                     String note, String statusBayar) {
        this.berat = berat;
        this.biaya = biaya;
        this.statusBayar = statusBayar;
        this.note = note;
        this.namaPelapak = namaPelapak;
        this.photoGuest = photoGuest;
        this.longitudeLaundry = longitudeLaundry;
        this.latitudeLaundry = latitudeLaundry;
        this.longitudeGuest = longitudeGuest;
        this.latitudeGuest = latitudeGuest;
        this.namaGuest = namaGuest;
        this.idGuest = idGuest;
        this.idLaundry = idLaundry;
        this.setrika = setrika;
        this.antarjemput = antarjemput;
        this.deskripsi = deskripsi;
        this.layanan = layanan;
        this.transKey = transKey;
        this.photoPelapak = photoPelapak;
        this.alamatPelapak = alamatPelapak;
        this.namaLaundry = namaLaundry;
        this.timeStamp = timeStamp;
        this.proses = proses;
    }

    public String getNote() {
        return note;
    }

    public String getNamaPelapak() {
        return namaPelapak;
    }

    public void setNamaPelapak(String namaPelapak) {
        this.namaPelapak = namaPelapak;
    }

    public String getPhotoGuest() {
        return photoGuest;
    }

    public void setPhotoGuest(String photoGuest) {
        this.photoGuest = photoGuest;
    }

    public String getLongitudeLaundry() {
        return longitudeLaundry;
    }

    public void setLongitudeLaundry(String longitudeLaundry) {
        this.longitudeLaundry = longitudeLaundry;
    }

    public String getLatitudeLaundry() {
        return latitudeLaundry;
    }

    public void setLatitudeLaundry(String latitudeLaundry) {
        this.latitudeLaundry = latitudeLaundry;
    }

    public String getLongitudeGuest() {
        return longitudeGuest;
    }

    public void setLongitudeGuest(String longitudeGuest) {
        this.longitudeGuest = longitudeGuest;
    }

    public String getLatitudeGuest() {
        return latitudeGuest;
    }

    public void setLatitudeGuest(String latitudeGuest) {
        this.latitudeGuest = latitudeGuest;
    }

    public String getNamaGuest() {
        return namaGuest;
    }

    public void setNamaGuest(String namaGuest) {
        this.namaGuest = namaGuest;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(String idLaundry) {
        this.idLaundry = idLaundry;
    }

    public String getSetrika() {
        return setrika;
    }

    public void setSetrika(String setrika) {
        this.setrika = setrika;
    }

    public String getAntarjemput() {
        return antarjemput;
    }

    public void setAntarjemput(String antarjemput) {
        this.antarjemput = antarjemput;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getTransKey() {
        return transKey;
    }

    public void setTransKey(String transKey) {
        this.transKey = transKey;
    }

    public String getPhotoPelapak() {
        return photoPelapak;
    }

    public void setPhotoPelapak(String photoPelapak) {
        this.photoPelapak = photoPelapak;
    }

    public String getAlamatPelapak() {
        return alamatPelapak;
    }

    public void setAlamatPelapak(String alamatPelapak) {
        this.alamatPelapak = alamatPelapak;
    }

    public String getNamaLaundry() {
        return namaLaundry;
    }

    public void setNamaLaundry(String namaLaundry) {
        this.namaLaundry = namaLaundry;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getProses() {
        return proses;
    }

    public void setProses(String proses) {
        this.proses = proses;
    }

    public String getStatusBayar() {
        return statusBayar;
    }

    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }
}
