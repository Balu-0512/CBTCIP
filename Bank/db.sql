
-- Structure of the databse for theBanking application of CipherByte-Technologies

-- Create the database
CREATE DATABASE userdb;

-- Use the database
USE userdb;

-- Create the users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create the accounts table
CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    balance DOUBLE NOT NULL,
    FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);

-- Insert initial data into the users table (optional)
INSERT INTO users (email, password) VALUES ('user1@example.com', 'password1');
INSERT INTO users (email, password) VALUES ('user2@example.com', 'password2');

-- Insert initial data into the accounts table (optional)
INSERT INTO accounts (email, balance) VALUES ('user1@example.com', 1000.00);
INSERT INTO accounts (email, balance) VALUES ('user2@example.com', 500.00);
