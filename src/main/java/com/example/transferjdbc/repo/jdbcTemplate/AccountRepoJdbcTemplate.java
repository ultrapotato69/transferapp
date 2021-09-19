package com.example.transferjdbc.repo.jdbcTemplate;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.repo.AccountRepo;
import com.example.transferjdbc.rowMapper.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class AccountRepoJdbcTemplate implements AccountRepo {

    private JdbcTemplate jdbcTemplate;
    private AccountRowMapper accountRowMapper;

    @Autowired
    public AccountRepoJdbcTemplate(JdbcTemplate jdbcTemplate, AccountRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountRowMapper = rowMapper;
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("select id, client_name, balance from account order by id", accountRowMapper);
    }

    @Override
    public Optional<Account> findById(Long id) {
        Account account = null;
        try {
           account = jdbcTemplate.queryForObject("select id, client_name, balance from account where id = ?",
                   accountRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        } finally {
            return Optional.ofNullable(account);
        }
    }

    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            return this.create(account);
        } else {
            return this.update(account);
        }
    }

    private Account create(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO account(client_name, balance) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getClient_name());
            ps.setBigDecimal(2, account.getBalance());
            return ps;
        }, keyHolder);
        account.setId((Long) keyHolder.getKeys().get("id"));
        return account;
    }

    private Account update(Account account) {
        Account accountFromDB = null;
        int row = jdbcTemplate.update("UPDATE account SET client_name = ?, balance = ? WHERE id = ?",
                account.getClient_name(),
                account.getBalance(),
                account.getId());
        if (row > 0) {
            accountFromDB = this.findById(account.getId()).orElseThrow();
        }
        return accountFromDB;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from account where id = ?", id);
    }
}
