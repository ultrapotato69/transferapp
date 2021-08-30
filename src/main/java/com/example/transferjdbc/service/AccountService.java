package com.example.transferjdbc.service;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Iterable<Account> findAll() {
        return accountRepo.findAll();
    }

    public Account findById(Long id) {
        return accountRepo.findById(id);
    }

    public Account save(Account account) {
        return accountRepo.save(account);
    }

    public void deleteById(Long id) {
        accountRepo.deleteById(id) ;
    }
}
