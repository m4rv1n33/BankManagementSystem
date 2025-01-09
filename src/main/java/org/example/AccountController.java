package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public String index() {
        return "index";
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
                        model.addAttribute("message", "Deposited " + amount + ". New balance is: " + account.getBalance());
                    } else {
                        model.addAttribute("message", "Invalid amount.");
                    }
                    break;
                case "withdraw":
                    if (amount != null && amount > 0 && amount <= account.getBalance()) {
                        account.setBalance(account.getBalance() - amount);
                        accountRepository.save(account);
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

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        return "error";
    }
}