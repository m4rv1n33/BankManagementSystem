package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/menu")
    public String showMenu(@RequestParam int accountId, Model model) {
        Account account = accountRepository.findById(accountId).orElse(null);
        model.addAttribute("account", account);
        return "menu";
    }

    @PostMapping("/selectAccount")
    public String selectAccount(@RequestParam int accountId, @RequestParam int pin, Model model) {
        Account selectedAccount = accountRepository.findById(accountId).orElse(null);
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
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            switch (action) {
                case "checkBalance":
                    model.addAttribute("message", "Your balance is: " + account.getBalance());
                    break;
                case "deposit":
                    if (amount != null && amount > 0) {
                        account.setBalance(account.getBalance() + amount);
                        accountRepository.save(account);
                        logTransaction(accountId, "deposit", amount);
                        model.addAttribute("message", "Deposited " + amount + ". New balance is: " + account.getBalance());
                    } else {
                        model.addAttribute("message", "Invalid amount.");
                    }
                    break;
                case "withdraw":
                    if (amount != null && amount > 0 && amount <= account.getBalance()) {
                        account.setBalance(account.getBalance() - amount);
                        accountRepository.save(account);
                        logTransaction(accountId, "withdraw", amount);
                        model.addAttribute("message", "Withdrew " + amount + ". New balance is: " + account.getBalance());
                    } else if (amount != null && amount > 0) {
                        model.addAttribute("message", "Insufficient funds.");
                    } else {
                        model.addAttribute("message", "Invalid amount.");
                    }
                    break;
            }
            model.addAttribute("account", account);
            return "menu";
        } else {
            model.addAttribute("error", "Account not found.");
            return "index";
        }
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam int fromAccountId, @RequestParam int toAccountId, @RequestParam double amount, Model model) {
        // Retrieve the "from" account
        Account fromAccount = accountRepository.findById(fromAccountId).orElse(null);
        if (fromAccount == null) {
            model.addAttribute("errorMessage", "Account not found.");
            return "transfer";
        }
        // Retrieve the "to" account
        Account toAccount = accountRepository.findById(toAccountId).orElse(null);
        if (toAccount == null) {
            model.addAttribute("errorMessage", "Account not found.");
            model.addAttribute("account", fromAccount);
            return "transfer";
        }

        // Validate the transfer amount
        if (amount <= 0) {
            model.addAttribute("errorMessage", "Invalid transfer amount.");
            model.addAttribute("account", fromAccount);
            return "transfer";
        }

        if (amount > fromAccount.getBalance()) {
            model.addAttribute("errorMessage", "Insufficient funds.");
            model.addAttribute("account", fromAccount);
            return "transfer";
        }

        // Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log transactions
        logTransaction(fromAccountId, "transfer_out", amount);
        logTransaction(toAccountId, "transfer_in", amount);

        // Success message and return to transfer page
        model.addAttribute("message", "Transferred " + amount + " to " + toAccount.getName());
        model.addAttribute("account", fromAccount);
        return "transfer";
    }


    @GetMapping("/transferPage")
    public String transferPage(@RequestParam int accountId, Model model) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            model.addAttribute("account", account);
            return "transfer";
        } else {
            model.addAttribute("errorMessage", "Account not found.");
            return "transfer";
        }
    }

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        return "error";
    }

    @PostMapping("/createAccount")
    public String createAccount(@RequestParam String name, @RequestParam int pin, Model model) {
        Account newAccount = new Account();
        newAccount.setName(name);
        newAccount.setPin(pin);
        newAccount.setBalance(0.0);
        accountRepository.save(newAccount);
        model.addAttribute("message", "Account created successfully. Your account ID is: " + newAccount.getId());
        return "index";
    }

    @GetMapping("/createAccountPage")
    public String createAccountPage() {
        return "createAccount";
    }

    private void logTransaction(int accountId, String type, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transaction);
    }
}