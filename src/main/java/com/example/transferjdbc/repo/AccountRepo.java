package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Account;

import java.util.Optional;

public interface AccountRepo {
    Iterable<Account> findAll();
    Optional<Account> findById(Long id);
    Account save(Account account);
    void deleteById(Long id);
}
