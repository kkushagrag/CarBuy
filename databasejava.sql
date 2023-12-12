CREATE DATABASE Cars;
USE Cars;

-- Create a table for cars
CREATE TABLE Cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    car_id int not null,
    category varchar(225) not null,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
);
select * from  Cars;
-- Create a table for customers
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Customer_id int not null,
    CustName VARCHAR(255) NOT NULL,
    email varchar(225) NOT NULL,
    CustomerPassword int not null
);

select * from customers;

-- Create a table for rented cars
CREATE TABLE sold_cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    Customer_id INT NOT NULL,
	soldDate DATE NOT NULL,
    Payment_method varchar(30),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (id) REFERENCES customers(id)
);

-- drop table sold_cars;

create table manager
(
	manager_id int primary key auto_increment,
	manager_name varchar(25) not null, 
	manager_email varchar(25) not null,
	manager_password varchar(15) not null
);    



select * from Cars;

INSERT INTO Cars (car_id, category, name, price) VALUES
-- Hatchbacks
(1, 'Hatchback', 'Toyota Yaris', 8.50),
(2, 'Hatchback', 'Honda Fit', 8.25),
(3, 'Hatchback', 'Ford Fiesta', 8.00),
(4, 'Hatchback', 'Volkswagen Golf', 9.00),
(5, 'Hatchback', 'Chevrolet Spark', 7.75),
(6, 'Hatchback', 'Hyundai Accent', 8.20),
(7, 'Hatchback', 'Nissan Micra', 8.10),
(8, 'Hatchback', 'Kia Rio', 8.30),
(9, 'Hatchback', 'Mazda 3', 8.75),
(10, 'Hatchback', 'Subaru Impreza', 9.50),
(11, 'Hatchback', 'Renault Clio', 9.25),
(12, 'Hatchback', 'Fiat 500', 8.40),
(13, 'Hatchback', 'Mini Cooper', 11.00),
(14, 'Hatchback', 'Suzuki Swift', 8.60),
(15, 'Hatchback', 'Dodge Caliber', 8.15),
-- SUVs
(16, 'SUV', 'Toyota RAV4', 28.00),
(17, 'SUV', 'Honda CR-V', 30.00),
(18, 'SUV', 'Ford Explorer', 35.00),
(19, 'SUV', 'Chevrolet Traverse', 32.50),
(20, 'SUV', 'Nissan Rogue', 28.75),
(21, 'SUV', 'Hyundai Santa Fe', 33.00),
(22, 'SUV', 'Kia Sorento', 30.75),
(23, 'SUV', 'Mazda CX-5', 29.50),
(24, 'SUV', 'Subaru Outback', 31.25),
(25, 'SUV', 'Jeep Cherokee', 34.00),
(26, 'SUV', 'Volkswagen Tiguan', 29.75),
(27, 'SUV', 'Audi Q5', 40.00),
(28, 'SUV', 'BMW X3', 42.50),
(29, 'SUV', 'Mercedes-Benz GLC', 45.00),
(30, 'SUV', 'Lexus RX', 48.00),
-- Sedans
(31, 'Sedan', 'Toyota Camry', 25.00),
(32, 'Sedan', 'Honda Accord', 27.00),
(33, 'Sedan', 'Ford Fusion', 26.00),
(34, 'Sedan', 'Chevrolet Malibu', 24.50),
(35, 'Sedan', 'Nissan Altima', 26.75),
(36, 'Sedan', 'Hyundai Sonata', 23.00),
(37, 'Sedan', 'Kia Optima', 22.50),
(38, 'Sedan', 'Mazda6', 28.50),
(39, 'Sedan', 'Subaru Legacy', 26.25),
(40, 'Sedan', 'Volkswagen Passat', 25.75),
(41, 'Sedan', 'Audi A4', 35.00),
(42, 'Sedan', 'BMW 3 Series', 37.50),
(43, 'Sedan', 'Mercedes-Benz C-Class', 40.00),
(44, 'Sedan', 'Lexus ES', 48.00),
(45, 'Sedan', 'Tesla Model 3', 55.00);

INSERT INTO customers (Customer_id, CustName, email, CustomerPassword) VALUES
(1, 'John Doe', 'john@example.com', 123456),
(2, 'Jane Smith', 'jane@example.com', 789012),
(3, 'Bob Johnson', 'bob@example.com', 345678),
(4, 'Alice Williams', 'alice@example.com', 901234),
(5, 'Charlie Brown', 'charlie@example.com', 567890),
(6, 'Eva Davis', 'eva@example.com', 112233),
(7, 'Michael Wilson', 'michael@example.com', 445566),
(8, 'Olivia Taylor', 'olivia@example.com', 778899),
(9, 'William Moore', 'william@example.com', 998877),
(10, 'Sophia Anderson', 'sophia@example.com', 665544),
(11, 'James Jackson', 'james@example.com', 332211),
(12, 'Emma Harris', 'emma@example.com', 554433),
(13, 'Benjamin Thomas', 'benjamin@example.com', 776655),
(14, 'Ava White', 'ava@example.com', 998877),
(15, 'Daniel Martinez', 'daniel@example.com', 112233),
(16, 'Mia Thompson', 'mia@example.com', 334455),
(17, 'Joseph Davis', 'joseph@example.com', 556677),
(18, 'Amelia Miller', 'amelia@example.com', 778899),
(19, 'David Brown', 'david@example.com', 990011),
(20, 'Olivia Harris', 'olivia@example.com', 223344),
(21, 'Alexander Wilson', 'alexander@example.com', 445566),
(22, 'Chloe Garcia', 'chloe@example.com', 667788),
(23, 'Ethan Smith', 'ethan@example.com', 889900),
(24, 'Isabella Moore', 'isabella@example.com', 112233),
(25, 'Sophie Turner', 'sophie@example.com', 445566);

INSERT INTO sold_cars (car_id, Customer_id, soldDate, Payment_method) VALUES
(1, 1, '2023-12-01', 'NET BANKING'),
(2, 2, '2023-12-02', 'CASH'),
(3, 3, '2023-12-03', 'DEBIT CARD'),
(4, 4, '2023-12-04', 'CREDIT CARD'),
(5, 5, '2023-12-05', 'DEBIT CARD'),
(6, 6, '2023-12-06', 'NET BANKING'),
(7, 7, '2023-12-07', 'CASH'),
(8, 8, '2023-12-08', 'NET BANKING'),
(9, 9, '2023-12-09', 'CASH'),
(10, 10, '2023-12-10', 'CREDIT CARD'),
(11, 11, '2023-12-11', 'DEBIT CARD'),
(12, 12, '2023-12-12', 'NET BANKING'),
(13, 13, '2023-12-13', 'CASH'),
(14, 14, '2023-12-14', 'CREDIT CARD'),
(15, 15, '2023-12-15', 'NET BANKING'),
(16, 16, '2023-12-16', 'CASH'),
(17, 17, '2023-12-17', 'DEBIT CARD'),
(18, 18, '2023-12-18', 'DEBIT CARD'),
(19, 19, '2023-12-19', 'CREDIT CARD'),
(20, 20, '2023-12-20', 'NET BANKING'),
(21, 21, '2023-12-21', 'CASH'),
(22, 22, '2023-12-22', 'CREDIT CARD'),
(23, 23, '2023-12-23', 'DEBIT CARD'),
(24, 24, '2023-12-24', 'NET BANKING'),
(25, 25, '2023-12-25', 'CASH');

insert into manager()
values
(1, 'Bhavesh Choudhary', 'bhavesh@gmail.com', '1234'),
(2, 'Piyush Garg', 'piyush@gmail.com', '1234'),
(3, 'Yash Sharma', 'yash@gmail.com', '1234'),
(4, 'Kushagra Gangwar', 'kushagra@gmail.com', '1234'),
(5, 'Manan Khandelwal', 'manan@gmail.com', '1234');


select *
from manager;

select  c.car_id, c.name, c.price,c.category, cs.customer_id, cs.custname,cs.email, s.soldDate, s.Payment_method
from sold_cars as s
left join cars as c on s.car_id = c.car_id
left join customers as cs on cs.customer_id = s.customer_id;


