package com.example.transferjdbc.repo.jdbcTemplate;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.repo.TransferRepo;
import com.example.transferjdbc.rowMapper.TransferRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Primary
public class TransferRepoJdbcTemplate implements TransferRepo {

    private final JdbcTemplate jdbcTemplate;
    private final TransferRowMapper transferRowMapper;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public TransferRepoJdbcTemplate(JdbcTemplate jdbcTemplate,
                                    TransferRowMapper transferRowMapper,
                                    PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transferRowMapper = transferRowMapper;
        this.transactionManager = transactionManager;
    }

    @Override
    public Transfer transferMoney(Transfer transfer) {

        Transfer fulfilledTransfer = null;
        Long transferId;
        Long from_account_id = transfer.getFrom_account_id();
        Long to_account_id = transfer.getTo_account_id();
        BigDecimal amount = transfer.getAmount();
        String comment = transfer.getComment();
        LocalDateTime localDateTime = LocalDateTime.now();

        BigDecimal balance = jdbcTemplate.queryForObject("select balance from account where id = ? for update",  BigDecimal.class, 1L);
        if (balance.compareTo(amount) >= 0) {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            jdbcTemplate.update("UPDATE account SET balance = balance - ? WHERE id = ?", amount, from_account_id);
            jdbcTemplate.update("UPDATE account SET balance = balance + ? WHERE id = ?", amount, to_account_id);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date) VALUES (? , ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, from_account_id);
                ps.setLong(2, to_account_id);
                ps.setBigDecimal(3, amount);
                ps.setString(4, comment);
                ps.setTimestamp(5, Timestamp.valueOf(localDateTime));
                return ps;
            }, keyHolder);
            transferId = (Long) keyHolder.getKeys().get("transfer_id");
            transactionManager.commit(status);
            fulfilledTransfer = new Transfer(transferId, from_account_id, to_account_id, amount, comment, localDateTime);
        }
        return fulfilledTransfer;
    }

    @Override
    public Iterable<Transfer> findAllTransfersById(Long clientId) {
        return jdbcTemplate.query(
                "select transfer_id, from_account_id, to_account_id, amount, comment, transfer_date " +
                        "from transfer where from_account_id = ? or to_account_id = ? order by transfer_id",
                transferRowMapper, clientId, clientId);
    }
}
