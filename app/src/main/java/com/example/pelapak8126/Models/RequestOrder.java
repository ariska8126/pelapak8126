package com.example.pelapak8126.Models;

public class RequestOrder {

    private String idLaundry;
    private String idGuest;
    private String deskripsi;
    private String paketLayanan;
    private Boolean setrika;
    private Boolean antarJemput;
    private Object timeStamp;
    private String orderKey;

    public RequestOrder(String idLaundry, String idGuest, String deskripsi,
                        String paketLayanan, Boolean setrika,
                        Boolean antarJemput) {

        this.idLaundry = idLaundry;
        this.idGuest = idGuest;
        this.deskripsi = deskripsi;
        this.paketLayanan = paketLayanan;
        this.setrika = setrika;
        this.antarJemput = antarJemput;
    }

    public RequestOrder() {
    }

    public String getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(String idLaundry) {
        this.idLaundry = idLaundry;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPaketLayanan() {
        return paketLayanan;
    }

    public void setPaketLayanan(String paketLayanan) {
        this.paketLayanan = paketLayanan;
    }

    public Boolean getSetrika() {
        return setrika;
    }

    public void setSetrika(Boolean setrika) {
        this.setrika = setrika;
    }

    public Boolean getAntarJemput() {
        return antarJemput;
    }

    public void setAntarJemput(Boolean antarJemput) {
        this.antarJemput = antarJemput;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
}
