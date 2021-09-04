package com.example.transferjdbc.rowMapper;

import com.example.transferjdbc.domain.Account;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(final ResultSet rs, final int rowNum) throws SQLException {
         return new Account(
                rs.getLong("id"),
                rs.getString("client_name"),
                rs.getBigDecimal("balance"));
    }
}
