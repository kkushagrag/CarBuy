package com.model;

import java.sql.Date;

public class sold_cars {
    private int car_id;
    private String car_name;
    private double price;
    String category;
    private int customer_id;
    private String custname;
    private String email;
    private Date soldDate;
    private String Payment_method;
    int choice;

    public sold_cars(int car_id, String name, double price, String category, int customer_id, String custname,
            String email, Date soldDate, String Payment_method) {
        this.car_id = car_id;
        this.car_name = name;
        this.price = price;
        this.category = category;
        this.customer_id = customer_id;
        this.custname = custname;
        this.email = email;
        this.soldDate = soldDate;
        this.Payment_method = Payment_method;
        choice =1;
    }

    public sold_cars(int car_id, String name, double price, String category, Date soldDate, String Payment_method) {
        this.car_id = car_id;
        this.car_name = name;
        this.price = price;
        this.category = category;
        this.soldDate = soldDate;
        this.Payment_method = Payment_method;
        choice=2;
    }

    @Override
    public String toString() {
        if(choice==2)
        {
            return "\u2588" +
                    "\n - Car ID: " + car_id +
                    "\n - Car Name: " + car_name +
                    "\n - Price: " + price +
                    "\n - Category: " + category +
                    "\n - Sold Date: " + soldDate +
                    "\n - Payment Method: " + Payment_method + "\n";
        }
        else{
            return "\u2588" +
                    "\n - Car ID: " + car_id +
                    "\n - Car Name: " + car_name +
                    "\n - Price: " + price +
                    "\n - Category: " + category +
                    "\n - Customer ID: " + customer_id +
                    "\n - Customer Name: " + custname +
                    "\n - Customer Email: " + email +
                    "\n - Sold Date: " + soldDate +
                    "\n - Payment Method: " + Payment_method + "\n";
        }
    
}

}