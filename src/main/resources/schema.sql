-- Create the database
DROP DATABASE IF EXISTS banksystem;
CREATE DATABASE IF NOT EXISTS banksystem;
USE banksystem;

-- Create the Account table
CREATE TABLE Account (
                         id INT PRIMARY KEY,
                         balance DECIMAL(10,2) NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         pin INT NOT NULL
);

-- Create the Transaction table
CREATE TABLE Transaction (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             account_id INT,
                             type VARCHAR(50),
                             amount DECIMAL(10,2),
                             timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Insert sample data into the Account table
INSERT INTO Account (id, balance, name, pin) VALUES
                                                 (1, 100.00, 'Marvin Strasser', 3333),
                                                 (2, 200.00, 'John Doe', 4444),
                                                 (3, 300.00, 'Jane Doe', 5555),
                                                 (4, 400.00, 'John Smith', 6666),
                                                 (5, 500.00, 'Jane Smith', 7777);