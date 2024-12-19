package org.example;
import java.util.Scanner;

/*
 * Author: Marvin Strasser
 * Version: 1.1
 * Date: 19/12/2024
 */
public class Main {

    // Create 5 accounts
    static Account[] accounts = {
            new Account(1, 100, "Marvin Strasser", 3333),
            new Account(2, 200, "John Doe", 4444),
            new Account(3, 300, "Jane Doe", 5555),
            new Account(4, 400, "John Smith", 6666),
            new Account(5, 500, "Jane Smith", 7777)
    };

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Declare scanner
        
        // Start ATM
        System.out.println("Welcome to the ATM.");
        int choice = selectAccount(input);
        Account selectedAccount = getAccountById(choice);
        if (selectedAccount != null) {
            System.out.println("You selected account ID: " + selectedAccount.getId());
            System.out.println("Welcome, " + selectedAccount.getName());
            displayMenu(input, selectedAccount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public static int selectAccount(Scanner input) {
        System.out.println("Please choose your account. (ID)");
        int choice = input.nextInt();
        if ((choice >= 1) && (choice <= 5)){
            Account selectedAccount = getAccountById(choice);

            //PIN Verification
            if (selectedAccount != null) {
                System.out.println("Please enter your PIN:");
                int enteredPin = input.nextInt();
                if (enteredPin == selectedAccount.getPin()) {
                    return choice;
                } else {
                    System.out.println("Incorrect PIN. Please try again.");
                    return selectAccount(input);
                }
            } else {
                System.out.println("Account not found. Please try again.");
                return selectAccount(input);
            }
        } else {
            System.out.println("Invalid account. Please try again.");
            return selectAccount(input);
        }
    }

    public static Account getAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    public static void displayMenu(Scanner input, Account account) {
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            int menuChoiceINT = input.nextInt();
            if (menuChoiceINT == 4) {
                System.out.println("Exiting... Thank you for using the ATM.");
                break;
            }
            else {
                switch (menuChoiceINT) {
                    case 1:
                        System.out.println("Checking balance...");
                        System.out.println("Your balance is: " + account.getBalance());
                        break;
                    case 2:
                        System.out.println("Deposit selected.");
                        System.out.println("Enter amount to deposit: ");
                        int depositAmount = input.nextInt();
                        account.setBalance(account.getBalance() + depositAmount);
                        System.out.println("Your new balance is: " + account.getBalance());
                        break;
                    case 3:
                        System.out.println("Withdraw selected.");
                        System.out.println("Available balance: " + account.getBalance());
                        System.out.println("Enter amount to withdraw: ");
                        int withdrawAmount = input.nextInt();
                        if (withdrawAmount > account.getBalance()) {
                            System.out.println("Insufficient funds!");
                        } else {
                            account.setBalance(account.getBalance() - withdrawAmount);
                            System.out.println("Your new balance is: " + account.getBalance());
                        }
                        break;
                    case 4:
                        System.out.println("Exiting... Thank you for using the ATM.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        displayMenu(input, account);
                        break;
                }
            }
        }
    }
}