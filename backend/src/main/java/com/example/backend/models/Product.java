package com.example.backend.models;

public class Product {

    private String name;

    private long id;
    public Product(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Product CreateSampleOffer(int id, String name){
        Product offer = new Product(id);
        offer.setName(name);
        return offer;
    }
}
