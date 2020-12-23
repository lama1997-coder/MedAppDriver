package com.magsood.medappuser.Model;

import java.util.ArrayList;

public class ModelProcessOrder {


    private String id;
    private String name_of_captin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_of_captin() {
        return name_of_captin;
    }

    public void setName_of_captin(String name_of_captin) {
        this.name_of_captin = name_of_captin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public ArrayList<String> getPharmacy_name() {
        return pharmacy_name;
    }

    public void setPharmacy_name(ArrayList<String> pharmacy_name) {
        this.pharmacy_name = pharmacy_name;
    }

    public ArrayList<String> getPharmacy_stage() {
        return pharmacy_stage;
    }

    public void setPharmacy_stage(ArrayList<String> pharmacy_stage) {
        this.pharmacy_stage = pharmacy_stage;
    }

    private String price;
    private String order_number;
    private ArrayList<String> pharmacy_name;
    private ArrayList<String> pharmacy_stage;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;
}
