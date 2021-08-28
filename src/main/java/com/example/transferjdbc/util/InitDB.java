package com.example.transferjdbc.util;

import com.example.transferjdbc.repo.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class InitDB {
    private final Connection connection;
    private final TransferRepo transferRepo;

    @Autowired
    public InitDB(ConnectionToDb connection, TransferRepo transferRepo) throws SQLException, IOException {
        this.connection = connection.getConnection();
        this.transferRepo = transferRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws SQLException {
        Statement stat = connection.createStatement();
        {
            createSchemaAccount(stat);
            createSchemaTransfer(stat);
            if(!selectTest(stat)){
                insertInitData(stat);
                insertTransfer(stat);
            }
        }
    }

    private void insertTransfer(Statement stat) {
        transferRepo.transferMoney(2L, 1L, new BigDecimal(5000), "for botinocki");
    }

    private void createSchemaAccount(Statement stat) throws SQLException {
        stat.executeUpdate("create TABLE IF NOT EXISTS ACCOUNT (" +
                "id serial, " +
                "client_name varchar(255), " +
                "balance numeric NOT NULL DEFAULT 0, " +
                "primary key (id))");
    }

    private void createSchemaTransfer(Statement stat) throws SQLException {
        stat.executeUpdate("CREATE TABLE IF NOT EXISTS transfer (" +
                "transfer_id serial, " +
                "from_account_id integer REFERENCES account (id), " +
                "to_account_id integer REFERENCES account (id), " +
                "amount numeric NOT NULL," +
                "transfer_date timestamp," +
                "comment varchar(255), " +
                "primary key (transfer_id))");
    }

    private void insertInitData(Statement stat) throws SQLException {
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Sergey Lebedev' , 500) " );
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Alexandr Smoleev' , 300000) " );

    }

    private boolean selectTest(Statement stat) throws SQLException {
        boolean isResult = false;
        try (ResultSet result = stat.executeQuery("SELECT * FROM account where id = 1"))
        {
            if (result.next()) {
                isResult = true;
            }
        } finally {
            return isResult;
        }
    }
}