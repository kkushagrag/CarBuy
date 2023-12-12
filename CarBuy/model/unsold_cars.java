package com.model;

import java.sql.Date;

public class unsold_cars {
    private String car_name;
    private int car_id;
    private String category;
    private double price;

    public unsold_cars(int car_id, String name, String category,double price) {
        this.car_id = car_id;
        this.car_name = name;
        this.category = category;
        this.price =price;
    }

    @Override
    public String toString() {
        return "\u2588" + " Car Name: " + car_name + 
                "\n  - Car ID: " + car_id + 
                "\n  - Category: " + category+          
                "\n  - Price: " + price +"\n";
    }

}