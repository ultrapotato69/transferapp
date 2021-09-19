package com.example.transferjdbc.repo.rawJdbc;

import com.example.transferjdbc.domain.Transfer;
//import com.example.transferjdbc.util.ConnectionToDb;
import com.example.transferjdbc.repo.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
//@Primary
public class TransferRepoRawJdbc implements TransferRepo {
    private Connection connection;

    @Autowired
    public TransferRepoRawJdbc(final DataSource dataSource) throws SQLException, IOException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public Transfer transferMoney(Transfer transfer) {
        Transfer fulfilledTransfer = null;

        Long transferId = null;
        BigDecimal amount = transfer.getAmount();
        Long from_account_id = transfer.getFrom_account_id();
        Long to_account_id = transfer.getTo_account_id();
        String comment = transfer.getComment();
        LocalDateTime localDateTime = LocalDateTime.now();


        BigDecimal balance;

        ResultSet createTransferRs;
        try (
                PreparedStatement selectForUpdateStatement =
                        connection.prepareStatement("select id, balance from account where id = ? for update");
                PreparedStatement subtractStatement =
                        connection.prepareStatement("UPDATE account SET balance = balance - ? WHERE id = ?");
                PreparedStatement addStatement =
                        connection.prepareStatement("UPDATE account SET balance = balance + ? WHERE id = ?");
                PreparedStatement createTransferStatement =
                        connection.prepareStatement("INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date) VALUES (? , ?, ?, ?, ?)",
                                PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            selectForUpdateStatement.setLong(1, from_account_id);
            ResultSet selectRs = selectForUpdateStatement.executeQuery();
            if (selectRs.next()) {
                balance = selectRs.getBigDecimal("balance");
                if (balance.compareTo(amount) >= 0) {
                    connection.setAutoCommit(false);

                    subtractStatement.setBigDecimal(1, amount);
                    subtractStatement.setLong(2, from_account_id);
                    subtractStatement.execute();

                    addStatement.setBigDecimal(1, amount);
                    addStatement.setLong(2, to_account_id);
                    addStatement.execute();

                    createTransferStatement.setLong(1, from_account_id);
                    createTransferStatement.setLong(2, to_account_id);
                    createTransferStatement.setBigDecimal(3, amount);
                    createTransferStatement.setString(4, comment);
                    createTransferStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
                    createTransferStatement.executeUpdate();
                    createTransferRs = createTransferStatement.getGeneratedKeys();

                    connection.commit();
                    connection.setAutoCommit(true);

                    if (createTransferRs.next()) {
                        transferId = createTransferRs.getLong("transfer_id");
                        fulfilledTransfer = new Transfer(transferId, from_account_id, to_account_id, amount, comment, localDateTime);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return fulfilledTransfer;
        }

    }

    @Override
    public Iterable<Transfer> findAllTransfersById(Long clientId) {
        List<Transfer> transferList = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "select transfer_id, from_account_id, to_account_id, amount, comment, transfer_date " +
                                     "from transfer where from_account_id = ? or to_account_id = ? order by transfer_id")) {
            statement.setLong(1, clientId);
            statement.setLong(2, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transfer transfer = new Transfer(
                        resultSet.getLong("transfer_id"),
                        resultSet.getLong("from_account_id"),
                        resultSet.getLong("to_account_id"),
                        resultSet.getBigDecimal("amount"),
                        resultSet.getString("comment"),
                        resultSet.getTimestamp("transfer_date").toLocalDateTime());
                transferList.add(transfer);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferList;
    }
}
