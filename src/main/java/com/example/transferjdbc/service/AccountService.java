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

    public Account create(Account account) {
        long generatedId = accountRepo.create(account.getClient_name(), account.getBalance());
        account.setId(generatedId);
        return account;
    }

    public Account update(Account account) {
        accountRepo.update(account.getId(), account.getClient_name(), account.getBalance());
        return accountRepo.findById(account.getId());
    }

    public Account delete(Long id) {
        Account account = accountRepo.findById(id);
        if (accountRepo.delete(id)) {
            return account;
        }
        return null;
    }
}
