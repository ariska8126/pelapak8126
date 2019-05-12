package com.example.pelapak8126.Models;

public class RequestOrder {

    String idGuest;
    String namaGuest;
    String photoGuest;
    String latitudeGuest;
    String longitudeGuest;

    String idLaundry;
    String namaLaundry;
    String photoPelapak;
    String namaPelapak;
    String alamatPelapak;
    String latitudeLaundry;
    String longitudeLaundry;

    String paketLayanan;
    String setrika;
    String antarJemput;
    String deskripsi;

    String status;

    String orderKey;
    String timeStamp;

    public RequestOrder() {

    }

    public RequestOrder(String idGuest, String namaGuest, String photoGuest,
                        String latitudeGuest, String longitudeGuest, String idLaundry,
                        String namaLaundry, String photoPelapak, String namaPelapak, String alamatPelapak,
                        String latitudeLaundry, String longitudeLaundry,
                        String paketLayanan, String setrika, String antarJemput,
                        String deskripsi, String status) {

        this.idGuest = idGuest;
        this.namaGuest = namaGuest;
        this.photoGuest = photoGuest;
        this.latitudeGuest = latitudeGuest;
        this.longitudeGuest = longitudeGuest;
        this.idLaundry = idLaundry;
        this.namaLaundry = namaLaundry;
        this.photoPelapak = photoPelapak;
        this.namaPelapak = namaPelapak;
        this.alamatPelapak = alamatPelapak;
        this.latitudeLaundry = latitudeLaundry;
        this.longitudeLaundry = longitudeLaundry;
        this.paketLayanan = paketLayanan;
        this.setrika = setrika;
        this.antarJemput = antarJemput;
        this.deskripsi = deskripsi;
        this.status = status;
    }

    public String getNamaLaundry() {
        return namaLaundry;
    }

    public void setNamaLaundry(String namaLaundry) {
        this.namaLaundry = namaLaundry;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getNamaGuest() {
        return namaGuest;
    }

    public void setNamaGuest(String namaGuest) {
        this.namaGuest = namaGuest;
    }

    public String getPhotoGuest() {
        return photoGuest;
    }

    public void setPhotoGuest(String photoGuest) {
        this.photoGuest = photoGuest;
    }

    public String getLatitudeGuest() {
        return latitudeGuest;
    }

    public void setLatitudeGuest(String latitudeGuest) {
        this.latitudeGuest = latitudeGuest;
    }

    public String getLongitudeGuest() {
        return longitudeGuest;
    }

    public void setLongitudeGuest(String longitudeGuest) {
        this.longitudeGuest = longitudeGuest;
    }

    public String getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(String idLaundry) {
        this.idLaundry = idLaundry;
    }

    public String getPhotoPelapak() {
        return photoPelapak;
    }

    public void setPhotoPelapak(String photoPelapak) {
        this.photoPelapak = photoPelapak;
    }

    public String getNamaPelapak() {
        return namaPelapak;
    }

    public void setNamaPelapak(String namaPelapak) {
        this.namaPelapak = namaPelapak;
    }

    public String getAlamatPelapak() {
        return alamatPelapak;
    }

    public void setAlamatPelapak(String alamatPelapak) {
        this.alamatPelapak = alamatPelapak;
    }

    public String getLatitudeLaundry() {
        return latitudeLaundry;
    }

    public void setLatitudeLaundry(String latitudeLaundry) {
        this.latitudeLaundry = latitudeLaundry;
    }

    public String getLongitudeLaundry() {
        return longitudeLaundry;
    }

    public void setLongitudeLaundry(String longitudeLaundry) {
        this.longitudeLaundry = longitudeLaundry;
    }

    public String getPaketLayanan() {
        return paketLayanan;
    }

    public void setPaketLayanan(String paketLayanan) {
        this.paketLayanan = paketLayanan;
    }

    public String getSetrika() {
        return setrika;
    }

    public void setSetrika(String setrika) {
        this.setrika = setrika;
    }

    public String getAntarJemput() {
        return antarJemput;
    }

    public void setAntarJemput(String antarJemput) {
        this.antarJemput = antarJemput;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
