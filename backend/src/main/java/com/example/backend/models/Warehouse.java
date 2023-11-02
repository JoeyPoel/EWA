package com.example.backend.models;

public class Warehouse {
    int id;
    String name;
    String location;
    String address;
    String postcode;
    String country;
    String contactName;
    String contactEmail;
    String contactPhone;
    String note;

    public Warehouse(int id) {
        this.id = id;
    }

    public static Warehouse CreateWarehouse(int id, String name, String location, String address, String postcode, String country, String contactName, String contactEmail, String contactPhone, String note){
        Warehouse warehouse = new Warehouse(id);
        warehouse.setName(name);
        warehouse.setLocation(location);
        warehouse.setAddress(address);
        warehouse.setPostcode(postcode);
        warehouse.setCountry(country);
        warehouse.setContactName(contactName);
        warehouse.setContactEmail(contactEmail);
        warehouse.setContactPhone(contactPhone);
        warehouse.setNote(note);
        return warehouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
