package com.magsood.medappuser.Model;

public class ModelCart {
    private String id;
    private String name;
    private String price;
    private String pharmacyName;
    private String pharmacyAddress;
    private String pharmacyLat;
    private String pharmacyLong;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyLat() {
        return pharmacyLat;
    }

    public void setPharmacyLat(String pharmacyLat) {
        this.pharmacyLat = pharmacyLat;
    }

    public String getPharmacyLong() {
        return pharmacyLong;
    }

    public void setPharmacyLong(String pharmacyLong) {
        this.pharmacyLong = pharmacyLong;
    }
}
