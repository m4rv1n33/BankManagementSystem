-- Create the database
DROP DATABASE IF EXISTS banksystem;
CREATE DATABASE IF NOT EXISTS banksystem;
USE banksystem;

-- Create the Account table
CREATE TABLE Account
(
    id      INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    balance DECIMAL(10, 2)                 NOT NULL DEFAULT '0',
    name    VARCHAR(100)                   NOT NULL,
    pin     INT                            NOT NULL
);

-- Create the Transaction table
CREATE TABLE Transaction
(
    id         INT AUTO_INCREMENT PRIMARY KEY      NOT NULL,
    account_id INT,
    type       VARCHAR(50),
    amount     DECIMAL(10, 2),
    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES Account (id)
);

-- Insert sample data into the Account table
INSERT INTO Account (name, pin)
VALUES ('Marvin Strasser', 1111),
       ('John Doe', 2222),
       ('Jane Doe', 3333),
       ('John Smith', 4444),
       ('Jane Smith', 5555);