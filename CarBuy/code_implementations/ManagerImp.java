package com.code_implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

import com.model.Manager;
import com.model.sold_cars;
import com.model.unsold_cars;
import com.util.DatabaseConnectivity;

import com.model.Customer;

public class ManagerImp {
    private Scanner scanner;
    private Connection conn;

    static void printLine() {
        System.out.println("----------------------------");
    }

    public ManagerImp(Scanner scanner) {
        this.scanner = scanner;
        this.conn = DatabaseConnectivity.provideConnection();
    }

    public void managerLogin() throws SQLException {
        System.out.print("\nEnter Manager email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Manager password: ");
        String password = scanner.nextLine();

        try {
            Manager m1 = checkLoginCredentials(email, password);
            System.out.println("\nHello " + m1.getManager_name() + ",\n");
            System.out.println(m1);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Manager checkLoginCredentials(String email, String password) throws SQLException {
        try {
            PreparedStatement ps = conn
                    .prepareStatement("select * from manager where manager_email=? AND manager_password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("manager_id");
                String name = rs.getString("manager_name");
                return new Manager(id, name, email, password);
            } else
                throw new SQLException("\nInvalid email or password\n");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void displayManagerInfo() {
        int m1Choice = 0;

        outerloop1:
        while (m1Choice != 2) {
            printLine();
            System.out.println("This is the Manager Dashboard.");
            System.out.println("-----------------------------");
            System.out.println("1. Display Sold Cars");
            System.out.println("2. Display Unsold Cars");
            System.out.println("3. Display Customers Details");
            System.out.println("4. Exit Manager Dashboard");
            System.out.println();

            try {
                System.out.print("Enter your choice: ");
                m1Choice = Integer.parseInt(scanner.next());
                scanner.nextLine();

                innerswitch1:
                switch (m1Choice) {
                    case 1:
                        sold_cars[] soldCars;

                        try {
                            soldCars = getSoldCars();
                            System.out.println("Sold Cars Details:");
                            printLine();
                            System.out.println();
                            for (sold_cars cars : soldCars) {
                                System.out.println(cars);
                            }
                            System.out.println();
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        break;
                    case 2:
                        unsold_cars[] unSoldCars;

                        try {
                            unSoldCars = getUnSoldCars();
                            System.out.println("UnSold Cars Report:");
                            printLine();
                            System.out.println();
                            for (unsold_cars cars : unSoldCars) {
                                System.out.println(cars);
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        break innerswitch1;   
                    case 3:
                        Customer[] customer;

                        try {
                            customer = getCustomersDetails();
                            System.out.println("Registered Customers Details:");
                            printLine();
                            System.out.println();
                            for (Customer cust : customer) {
                                System.out.println(cust);
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        break;
                    case 4:
                        closeDatabaseConnection();
                        System.out.println();
                        System.out.println("Exiting Manager Dashboard...");
                        System.out.println();
                        break outerloop1;
                    default:
                        System.out.println("\nInvalid Option. Please enter a number between 1 and 4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.\n");
            }
        }
    }

    public sold_cars[] getSoldCars() throws SQLException {
        try {
            String query = "select  c.car_id, c.name, c.price,c.category, cs.customer_id, cs.custname,cs.email, s.soldDate, s.Payment_method from sold_cars as s left join cars as c on s.car_id = c.car_id left join customers as cs on cs.customer_id = s.customer_id;";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            sold_cars[] soldCarsDetails = new sold_cars[rowCount];
            int index = 0;

            while (rs.next()) {
                int car_id = rs.getInt("car_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                int customer_id = rs.getInt("customer_id");
                String custname = rs.getString("custname");
                String email = rs.getString("email");
                Date soldDate = rs.getDate("soldDate");
                String payment_method = rs.getString("Payment_method");

                soldCarsDetails[index++] = new sold_cars(car_id, name, price, category, customer_id, custname, email,
                        soldDate, payment_method);
            }

            return soldCarsDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving Sold Car Details.");
        }
    }

    public unsold_cars[] getUnSoldCars() throws SQLException {
        try {
            String query = "SELECT * FROM Cars WHERE car_id NOT IN (SELECT DISTINCT car_id FROM sold_cars)";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            unsold_cars[] unsoldCarsDetails = new unsold_cars[rowCount];
            int index = 0;

            while (rs.next()) {
                int carId = rs.getInt("car_id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                unsoldCarsDetails[index++] = new unsold_cars(carId,  name,category, price);
            }

            return unsoldCarsDetails;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving Unsold Car Details.");
        }
    }

    public Customer[] getCustomersDetails() throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from customers", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Customer[] customerDetails = new Customer[rowCount];
            int index = 0;
            while (rs.next()) {
                String name = rs.getString("CustName");
                String email = rs.getString("email");
                String password = rs.getString("CustomerPassword");

                customerDetails[index++] = new Customer(name, email, password);
            }
            return customerDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving buyer details");
        }
    }

    public void closeDatabaseConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
