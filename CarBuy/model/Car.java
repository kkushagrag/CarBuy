package com.model;

public class Car {
    private int car_id;
    private String name;
    private double price;

    public Car(int car_id,String name, double price) {
        this.car_id = car_id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return  " Car:" +car_id + " name:" +name+" price:"+price;
    }
}