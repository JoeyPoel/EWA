package com.example.backend.models;

public class Warehouse {
    int id;

    String name;

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

    public Warehouse(int id) {
        this.id = id;
    }

    public static Warehouse CreateSampleWarehouse(int id, String name){
        Warehouse warehouse = new Warehouse(id);
        warehouse.setName(name);
        return warehouse;
    }
}
