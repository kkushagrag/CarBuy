package com.code_implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;

import com.util.DatabaseConnectivity;
import com.util.FileHandling1;
import com.model.unsold_cars;
import com.model.Customer;
import com.model.sold_cars;

public class CustomerImp {
    private Scanner scanner;
    private Connection conn;
    private Customer c1;

    static void printLine() {
        System.out.println("----------------------------");
    }

    public CustomerImp(Scanner scanner) {
        this.scanner = scanner;
        this.conn = DatabaseConnectivity.provideConnection();
    }

    public String registerCustomer(Customer c) throws SQLException {
        String message = "Registration Unsuccessful";

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO customers(CustName, email, CustomerPassword) VALUES ( ?, ?, ?)");
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPassword());

            int x = ps.executeUpdate();
            if (x > 0) {
                message = "Registration successful";
            } else {
                throw new SQLException(message);
            }
        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }

    public boolean isEmailAlreadyRegistered(String email) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM customers WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isNameAlreadyRegistered(String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM customers WHERE CustName=?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void customerLogin() throws SQLException {
        System.out.print("\nEnter Customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Customer password: ");
        String password = scanner.nextLine();

        try {
            c1 = checkLoginCredentials(email, password);
            System.out.println("\nHello " + c1.getName() + ",\n");
            System.out.println(c1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer checkLoginCredentials(String email, String password) throws SQLException {
        try {
            PreparedStatement ps = conn
                    .prepareStatement("select * from customers where email=? AND CustomerPassword=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("customer_id");
                String name = rs.getString("CustName");
                return new Customer(id, name, email, password);
            } else
                throw new SQLException("Invalid email or password\n");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void start() {
        int c1Choice = 0;

        while (c1Choice != 4) {
            FileHandling1.showOptions();
            System.out.println();

            try {
                System.out.print("Enter your choice: ");
                c1Choice = scanner.nextInt();
                scanner.nextLine();

                switch (c1Choice) {
                    case 1:
                        viewCars();
                        break;
                    case 2:
                        System.out.print("Enter Car ID you want to buy: ");
                        try {
                            int car_id_to_buy = Integer.parseInt(scanner.nextLine());
                            if (checkIfCarExist(car_id_to_buy)) {
                                unsold_cars car = getCarById(car_id_to_buy);
                                System.out.println();
                                System.out.println(car);

                                while (true) {
                                    System.out.print("Are you sure you want to buy this car(1/0): ");
                                    System.out.println();
                                    try {
                                        int answer = Integer.parseInt(scanner.nextLine());
                                        if (answer == 1) {
                                            System.out.println("Select Payment Method: [1. NET BANKING 2. CASH 3. CREDIT CARD 4. DEBIT CARD ]");
                                            int paymentWay = Integer.parseInt(scanner.nextLine());

                                            String result_ = buyTheProduct(car_id_to_buy, c1, paymentWay );
                                            System.out.println();
                                            System.out.println();
                                            System.out.println(result_);
                                            System.out.println();
                                            sold_cars soldCarDetails = printSoldCarDetails(c1);
                                            System.out.println("Your Purchase Summary:- :) ");
                                            printLine();
                                            System.out.println(soldCarDetails);
                                            System.out.println();
                                            break;
                                        } else if (answer == 0) {
                                            break;
                                        }else{
                                            System.out.println();
                                            System.out.println("Invalid input: Press Enter 1 or 0");
                                            System.out.println();  
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println();
                                        System.out.println("Invalid input: Press Enter 1 or 0");
                                        System.out.println();
                                    }
                                }
                            } else {
                                System.out.println("Car does not exist or already been Sold");
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        // buyACar();
                        break;
                    case 3:
                        try{
                            sold_cars[] CarsDetails = viewBuyingHistory();
                            System.out.println("\n");
                            System.out.println("Your Purchase History");
                            printLine();
                            for(sold_cars cars:CarsDetails){
                                System.out.println(cars);
                            }

                        }catch (SQLException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        closeDatabaseConnection();
                        System.out.println("");
                        System.out.println("Exiting CarBuy Portal. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("\nInvalid Option. Please enter a number between 1 and 4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.\n");
            }
        }
    }

    public sold_cars printSoldCarDetails(Customer c1) throws SQLException {
        try {
            String query = "select  c.car_id, c.name, c.price,c.category, cs.customer_id, cs.custname,cs.email, s.soldDate, s.Payment_method from sold_cars as s left join cars as c on s.car_id = c.car_id left join customers as cs on cs.customer_id = s.customer_id where s.Customer_id = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, c1.getCustomerId());
            ResultSet rs = ps.executeQuery();

            sold_cars[] soldCar = new sold_cars[1];
            if (rs.next()) {
                int car_id = rs.getInt("car_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                int customer_id = rs.getInt("customer_id");
                String custname = rs.getString("custname");
                String email = rs.getString("email");
                Date soldDate = rs.getDate("soldDate");
                String payment_method = rs.getString("Payment_method");

                soldCar[0] = new sold_cars(car_id, name, price, category, customer_id, custname, email,
                        soldDate, payment_method);
            }

            return soldCar[0];
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving Sold Car Details.");
        }
    }

    public sold_cars[] viewBuyingHistory() throws SQLException {
        try {
            String query = "SELECT * FROM Sold_cars AS s LEFT JOIN cars AS c ON c.car_id = s.car_id WHERE s.customer_id = ?;";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, c1.getCustomerId());
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            sold_cars[] CarsDetails = new sold_cars[rowCount];
            int index = 0;
            while (rs.next()) {
                int id = rs.getInt("car_id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                Date SoldDate = rs.getDate("SoldDate");
                String Payment_method = rs.getString("Payment_method");

                CarsDetails[index++] = new sold_cars(id, name, price, category, SoldDate, Payment_method);
            }
            return CarsDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving Buying History");
        }
    }

    public String buyTheProduct(int car_id_to_buy, Customer c1,int paymentWay) {
        String message = "Car not Sold";
        String[] paymentMethods = { "NET BANKING", "CASH", "CREDIT CARD", "DEBIT CARD" };
        try {
            LocalDate currentDate = LocalDate.now();
            Date soldDate = Date.valueOf(currentDate);

            PreparedStatement ps = conn.prepareStatement(
                    "insert into sold_cars(car_id,Customer_id,soldDate,Payment_method) values(?,?,?,?)");
            ps.setInt(1, car_id_to_buy);
            ps.setInt(2, c1.getCustomerId());
            ps.setDate(3, soldDate);
            ps.setString(4,paymentMethods[paymentWay-1]);

            int x = ps.executeUpdate();
            if (x > 0)
                message = "Car Sold Successfully";
            else
                throw new SQLException(message);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    public boolean checkIfCarExist(int car_id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Cars WHERE car_id NOT IN (SELECT DISTINCT car_id FROM sold_cars) AND car_id=?")) {
            ps.setInt(1, car_id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public unsold_cars getCarById(int car_id) throws SQLException {
        try {
            String query = "SELECT * FROM Cars WHERE car_id=?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, car_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int carId = rs.getInt("car_id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                return new unsold_cars(carId, name, category, price);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving Unsold Car Details.");
        }
        return null;
    }

    public void viewCars() {
        int c2Choice = 0;
        while (c2Choice != 4) {
            System.out.println("Enter Car Category: [1. Hatchback, 2. SUV, 3. Sedan 4. Exit]");
            unsold_cars[] unsoldCars;

            try {
                c2Choice = Integer.parseInt(scanner.nextLine());

                System.out.println("Displaying Unsold Cars...");
                printLine();
                System.out.println();

                switch (c2Choice) {
                    case 1:
                        unsoldCars = getUnsoldCars("Hatchback");
                        for (unsold_cars car : unsoldCars) {
                            System.out.println(car);
                        }
                        printLine();
                        break;
                    case 2:
                        unsoldCars = getUnsoldCars("SUV");
                        for (unsold_cars car : unsoldCars) {
                            System.out.println(car);
                        }
                        printLine();
                        break;
                    case 3:
                        unsoldCars = getUnsoldCars("Sedan");
                        for (unsold_cars car : unsoldCars) {
                            System.out.println(car);
                        }
                        printLine();
                    case 4:
                        break;
                    default:
                        System.out.println("\nInvalid Option. Please enter a number between 1 and 4.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.\n");
            } catch (SQLException e) {
                System.out.println("Error retrieving unsold cars data");
            }
        }
    }

    public unsold_cars[] getUnsoldCars(String category) throws SQLException {
        try {
            String query = "SELECT * FROM Cars WHERE car_id NOT IN (SELECT DISTINCT car_id FROM sold_cars) AND category=?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            unsold_cars[] unsoldCarsDetails = new unsold_cars[rowCount];
            int index = 0;

            while (rs.next()) {
                int carId = rs.getInt("car_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                unsoldCarsDetails[index++] = new unsold_cars(carId, name, category, price);
            }

            return unsoldCarsDetails;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error retrieving Unsold Car Details.");
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
