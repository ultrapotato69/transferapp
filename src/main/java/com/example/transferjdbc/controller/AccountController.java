package com.example.transferjdbc.controller;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.repo.AccountRepo;
import com.example.transferjdbc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountRepo accountRepo;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountRepo accountRepo, AccountService accountService) {
        this.accountRepo = accountRepo;
        this.accountService = accountService;
    }

    @GetMapping
    public Iterable<Account> listAccount(){
        return accountRepo.findAll();
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepo.findById(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
       return accountService.create(account);
    }

    @PutMapping
    public Account updateAccount(@RequestBody Account account){
       return accountService.update(account);
    }

    @DeleteMapping("{id}")
    public Account deleteAccount(@PathVariable("id") Long id) {
        return accountService.delete(id);
    }

}