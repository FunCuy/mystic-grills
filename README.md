# Mystic Grills

Mystic Grills is an application designed for restaurant management, aiming to simplify operational processes. Equipped with functionalities such as user administration, customizable menus, and an efficient ordering system, Mystic Grills offers a user-friendly and cohesive experience for both restaurant personnel and patrons. The app aspires to transform the landscape of restaurant management by utilizing technology to boost operational efficiency, elevate customer contentment, and contribute to the overall success of dining establishments.

## SQL Schema
```sql
CREATE DATABASE mysticgrills;

CREATE TABLE users(
    userId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userRole VARCHAR(20) NOT NULL,
    userName VARCHAR(30) NOT NULL,
    userEmail VARCHAR(30) NOT NULL,
    userPassword VARCHAR(30) NOT NULL  
);

CREATE TABLE menuitem(
    menuItemId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    menuItemName VARCHAR(30) NOT NULL,
    menuItemDescription VARCHAR(30) NOT NULL,
    menuItemPrice INT NOT NULL 
);

CREATE TABLE orderitem(
    orderItemId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    menuItemId INT NOT NULL,
    quantity INT NOT NULL,
    
    CONSTRAINT fk_menu_item_id FOREIGN KEY (menutItemId) REFERENCES menuitem(menutItemId)
    
);

CREATE TABLE orders(
    orderId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    orderItemId INT NOT NULL,
    userId INT NOT NULL,
    orderStatus VARCHAR(30) NOT NULL,
    orderDate DATE NOT NULL,
    orderTotal INT NOT NULL,
    
    CONSTRAINT fk_order_item_id FOREIGN KEY (orderItemId) REFERENCES orderitem(orderItemId),
    CONSTRAINT fk_user_id FOREIGN KEY (userId) REFERENCES users(userId)  
);

CREATE TABLE receipt(
    receiptId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    orderId INT NOT NULL,
    receiptPaymentAmount INT NOT NULL,
    receiptPaymentDate DATE NOT NULL,
    receiptPaymentType VARCHAR(30) NOT NULL,
    
    
    CONSTRAINT fk_order_id FOREIGN KEY (orderId) REFERENCES orders(orderId)  
);
```
