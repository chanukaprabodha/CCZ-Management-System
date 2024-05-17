CREATE DATABASE ccz;

USE ccz;

CREATE TABLE customer (
                          customerId VARCHAR(30) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE,
                          address VARCHAR(255),
                          phone VARCHAR(20) UNIQUE
);


CREATE TABLE orders (
                        orderId VARCHAR(30) PRIMARY KEY,
                        orderDate DATE NOT NULL,
                        customerId VARCHAR(30) NOT NULL,
                        totalAmount double(10,2) not null,
                        CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE product (
                         productId VARCHAR(30) PRIMARY KEY,
                         productName VARCHAR(255) NOT NULL,
                         unitPrice double(10,2) NOT NULL
);

CREATE TABLE orderDetail (
                             orderId VARCHAR(30) NOT NULL,
                             ing_Id VARCHAR(30) NOT NULL,
                             productQuantity double(10,2) NOT NULL,
                             unitPrice double(10,2) NOT NULL,
                             CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders(orderId) ON UPDATE CASCADE ON DELETE CASCADE,
                             CONSTRAINT FOREIGN KEY (ing_Id) REFERENCES ingredient(ing_Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ingredient (
                            ing_Id VARCHAR(30) PRIMARY KEY,
                            ing_name VARCHAR(255) NOT NULL,
                            stock double(10,2) not null,
                            price double(10,2) not null
);
INSERT INTO ingredient (ing_Id, ing_name, stock, price)
VALUES
    ('101', 'vanillaBase(1kg)', 3, 2500.00),
    ('102', 'chocolateBase(1kg)', 3, 3000.00),
    ('103', 'coffeeBase(1kg)', 3, 3500.00),
    ('104', 'cupupCakes(6pcs)', 3, 500.00),
    ('105', 'essence(1dos)', 3, 50.00),
    ('106', 'coloring(1dos)', 3, 50.00),
    ('107', 'toppers', 3, 250.00),
    ('108', 'icing(1dos)', 3, 1000.00),
    ('109', 'packingItems', 3, 300.00);

CREATE TABLE recipe (
                        productId VARCHAR(30) NOT NULL,
                        ingredientId VARCHAR(30) NOT NULL,
                        ingredientQuantity INT NOT NULL,
                        CONSTRAINT FOREIGN KEY (ingredientId) REFERENCES ingredient(ing_Id) ON UPDATE CASCADE ON DELETE CASCADE,
                        CONSTRAINT FOREIGN KEY (productId) REFERENCES product(productId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE employee (
                          employeeId VARCHAR(30) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          position VARCHAR(255) NOT NULL,
                          address varchar(255) not null,
                          contact varchar(15) not null
);
INSERT INTO employee (employeeId,name,position,address,contact)
VALUES
    ('e001','Dolby', 'Admin','testAdress', '0123456789' );

CREATE TABLE cardinality (
                             employeeId VARCHAR(30) NOT NULL,
                             userId VARCHAR(30) PRIMARY KEY,
                             Password VARCHAR(255) NOT NULL,
                             CONSTRAINT FOREIGN KEY (employeeId) REFERENCES employee(employeeId) ON UPDATE CASCADE ON DELETE CASCADE
);
INSERT INTO cardinality (employeeId,userId,Password)
VALUES
    ('e001', 'u001', 'Admin@123');
