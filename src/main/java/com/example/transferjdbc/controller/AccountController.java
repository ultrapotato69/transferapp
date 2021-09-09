package com.example.transferjdbc.controller;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("account")

public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController( AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Iterable<Account> listAccount(){
        return accountService.findAll();
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.findById(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        if (account.getId() != null || account.getClient_name() == null || account.getClient_name().equals("")) {
            return null;
        } else {
            return accountService.save(account);
        }
    }

    @PutMapping()
    public Account updateAccount(@RequestBody Account account){
       return accountService.save(account);
    }

    @DeleteMapping("{id}")
    public void deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteById(id);
    }

}