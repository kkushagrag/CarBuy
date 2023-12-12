package CarBuy;

import com.code_implementations.ManagerImp;
import com.code_implementations.CustomerImp;
import com.model.Car;
import com.util.FileHandling1;

import com.model.Manager;
import com.model.sold_cars;
import com.model.Customer;
import com.util.DatabaseConnectivity;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void printLine() {
        System.out.println("----------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("   Welcome to the Portal!");
        printLine();

        int modeChoice = 0;

        while (modeChoice != 3) {
            System.out.println();
            printLine();
            System.out.println("Choose the mode:");
            printLine();
            System.out.println("1. Customer");
            System.out.println("2. Manager");
            System.out.println("3. Exit Portal.");
            System.out.println();

            try {
                System.out.print("Enter your choice: ");
                modeChoice = Integer.parseInt(scanner.next());
                scanner.nextLine();

                hello: switch (modeChoice) {
                    case 1:
                        CustomerImp c1Imp = new CustomerImp(scanner);
                        System.out.println("Entering Car World...");

                        int choice1 = 0;
                        
                        while2:
                        while (choice1 != 3) {
                            System.out.println("\nPress 1)Login  2)Register  3) Exit");
                            try {
                                choice1 = Integer.parseInt(scanner.nextLine());
                                
                                switch (choice1) {
                                    case 1: {
                                        try {
                                            c1Imp.customerLogin();
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                            break;
                                        }
                                        c1Imp.start();
                                        break while2;
                                    }
                                    case 2: {
                                        try {
                                            // Register
                                            System.out.print("\nEnter E-mail: ");
                                            String cemail = scanner.nextLine();

                                            if (c1Imp.isEmailAlreadyRegistered(cemail)) {
                                                System.out.println("Email is already registered. Please try Again.");
                                                break;
                                            }

                                            System.out.print("Enter Name :");
                                            String cname = scanner.nextLine();

                                            if (c1Imp.isNameAlreadyRegistered(cname)) {
                                                System.out.println("Name is already registered. Please try Again.");
                                                break;
                                            }

                                            System.out.print("Enter password:");
                                            String cpass = scanner.nextLine();
                                            System.out.println();
                                            Customer customer = new Customer(cname, cemail, cpass);
                                            String message = c1Imp.registerCustomer(customer);
                                            FileHandling1.writecustomerDetailsToFile(customer);
                                            System.out.println(message);

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        // catch (IOException e) {
                                        // e.printStackTrace();
                                        // }
                                        break;
                                    }
                                    case 3: {
                                        break hello;
                                    }
                                    default:
                                        System.out.println("\nInvalid Option. Please enter a number between 1 and 3.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("\nInvalid input. Please enter a valid number.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    case 2:
                        ManagerImp m1Imp = new ManagerImp(scanner);
                        System.out.println();
                        System.out.println("Entering Manager Mode...");

                        try {
                            m1Imp.managerLogin();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                            break;
                        }

                        m1Imp.displayManagerInfo();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("\nInvalid Option. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
            }
        }

    }
}
