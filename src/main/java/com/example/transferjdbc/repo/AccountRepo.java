package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Account;

public interface AccountRepo {
    Iterable<Account> findAll();
    Account findById(Long id);
    Account save(Account account);
    void deleteById(Long id);
}
