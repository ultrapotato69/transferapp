package com.example.transferjdbc.rowMapper;

import com.example.transferjdbc.domain.Transfer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransferRowMapper implements RowMapper<Transfer> {
    @Override
    public Transfer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Transfer(
                rs.getLong("transfer_id"),
                rs.getLong("from_account_id"),
                rs.getLong("to_account_id"),
                rs.getBigDecimal("amount"),
                rs.getString("comment"),
                rs.getTimestamp("transfer_date").toLocalDateTime());
    }
}
