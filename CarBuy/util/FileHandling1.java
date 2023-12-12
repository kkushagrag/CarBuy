package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.model.Customer;


public class FileHandling1 {
    
    static public void showOptions() {
        try {
            File file = new File("userOptions.txt");
            Scanner scanner = new Scanner(file);
            boolean isInSection = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                System.out.println(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writecustomerDetailsToFile(Customer customer) throws IOException {
        try (FileWriter writer = new FileWriter("customerRegistration.txt",true)) {

            writer.write("New customer Registered:\n");
            writer.write("Name: " + customer.getName() + "\n");
            writer.write("Email: " + customer.getEmail() + "\n");
            writer.write("Password: " + customer.getPassword() + "\n");
            writer.write("END\n\n");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}