package org.example;
/*
 * Author: Marvin Strasser
 * Version: 1.1
 * Date: 19/12/2024
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    static Account[] accounts = {
            new Account(1, 100, "Marvin Strasser", 3333),
            new Account(2, 200, "John Doe", 4444),
            new Account(3, 300, "Jane Doe", 5555),
            new Account(4, 400, "John Smith", 6666),
            new Account(5, 500, "Jane Smith", 7777)
    };

    @GetMapping("")
    public String index() {
        return "index";
    }

    @PostMapping("/selectAccount")
    public String selectAccount(@RequestParam int accountId, @RequestParam int pin, Model model) {
        Account selectedAccount = getAccountById(accountId);
        if (selectedAccount != null && selectedAccount.getPin() == pin) {
            model.addAttribute("account", selectedAccount);
            return "menu";
        } else {
            model.addAttribute("error", "Invalid account ID or PIN.");
            return "index";
        }
    }

    @PostMapping("/menu")
    public String menu(@RequestParam int accountId, @RequestParam String action, @RequestParam(required = false) Integer amount, Model model) {
        Account account = getAccountById(accountId);
        if (account != null) {
            switch (action) {
                case "checkBalance":
                    model.addAttribute("message", "Your balance is: " + account.getBalance());
                    break;
                case "deposit":
                    if (amount != null) {
                        account.setBalance(account.getBalance() + amount);
                        model.addAttribute("message", "Deposited " + amount + ". New balance is: " + account.getBalance());
                    }
                    break;
                case "withdraw":
                    if (amount != null && amount <= account.getBalance()) {
                        account.setBalance(account.getBalance() - amount);
                        model.addAttribute("message", "Withdrew " + amount + ". New balance is: " + account.getBalance());
                    } else {
                        model.addAttribute("message", "Insufficient funds.");
                    }
                    break;
               // case "exit":
                  //  return "index";
            }
            model.addAttribute("account", account);
            return "menu";
        } else {
            model.addAttribute("error", "Account not found.");
            return "index";
        }
    }

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        return "error";
    }

    private Account getAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }
}