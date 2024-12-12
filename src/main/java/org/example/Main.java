package org.example;
import java.util.Scanner;

/*
 * Author: Marvin Strasser
 * Version: 1.0
 * Date: 10/12/2024
 */
public class Main {

    // Create 5 accounts
    Account account0 = new Account(1, 100, "Marvin Strasser", 3333);
    Account account1 = new Account(2, 200, "John Doe", 4444);
    Account account2 = new Account(3, 300, "Jane Doe", 5555);
    Account account3 = new Account(4, 400, "John Smith", 6666);
    Account account4 = new Account(5, 500, "Jane Smith", 7777);
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Start ATM
        System.out.println("Welcome to the ATM.");
        int choice = selectAccount(input);
        System.out.println("You selected account ID: " + choice);
        String menuChoice = "If you see this, something went wrong.";
        System.out.println("Initialising ATM.");
        displayMenu(choice);
    }

    public static int selectAccount(Scanner input) {
        System.out.println("Please choose your account. (ID)");
        int choice = input.nextInt();
        if ((choice >= 1) && (choice <= 5)){
            return choice;
        } else {
            System.out.println("Invalid account. Please try again.");
            return selectAccount(input);
        }
    }

    public static String displayMenu(int choice, Scanner input) {
        System.out.println("Please select an option:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        int menuChoiceINT = input.nextInt();
        switch (menuChoiceINT) {
            case 1:
                System.out.println("Checking balance...");
                wait(2000);
                System.out.println("Your balance is: " + account+choice.getBalance());
                return "Check Balance";
            case 2:
                menuChoice = "Deposit";
                return "Deposit";
            case 3:
                menuChoice = "Withdraw";
                return "Withdraw";
            case 4:
                menuChoice = "Exit";
                return "Exit";
            default:
                System.out.println("Invalid option. Please try again.");
                return displayMenu(input,menuChoice);
        }
    }
}