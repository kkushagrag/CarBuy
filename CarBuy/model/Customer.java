package com.model;

public class Customer {
    private int Customer_id;
    private String CustName;
    private String email;
    private String CustomerPassword;
    
    public Customer(int Customer_id,String CustName,String email,String CustomerPassword){
        this.CustName = CustName;
        this.CustomerPassword = CustomerPassword;
        this.email = email;
        this.Customer_id = Customer_id;
    }

    public Customer(String CustName,String email,String CustomerPassword){
        this.CustName = CustName;
        this.CustomerPassword = CustomerPassword;
        this.email = email;
        this.Customer_id=-1;
    }

    public int getCustomerId(){
        return Customer_id;
    }
    
    public String getName(){
        return CustName;
    } 

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return CustomerPassword;
    }

    @Override
	public String toString() {
        if(Customer_id ==-1){
            return "Your Customer Details:\n[ID: " + Customer_id +"]\nName: " + CustName + "\nEmail: " + email
                    + "\nPassword " +CustomerPassword+"\n";
        } else{
            return "Your Customer Details:\nName: " + CustName + "\nEmail: " + email
                    + "\nPassword " +CustomerPassword+"\n";
        }
	}
}
