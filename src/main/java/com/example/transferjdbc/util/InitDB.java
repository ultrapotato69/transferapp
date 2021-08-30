package com.example.transferjdbc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class InitDB {
    private final Connection connection;


    @Autowired
    public InitDB(ConnectionToDb connection) throws SQLException, IOException {
        this.connection = connection.getConnection();

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

    private void insertTransfer(Statement stat) throws SQLException {
        stat.executeUpdate("UPDATE account SET balance = balance - 5000 WHERE id = 2;" +
                "UPDATE account SET balance = balance + 5000 WHERE id = 1;" +
                "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)" +
                "VALUES (2 , 1, 5000, 'for botinocki', current_timestamp);");
         stat.executeUpdate("UPDATE account SET balance = balance - 200 WHERE id = 2;" +
                "UPDATE account SET balance = balance + 200 WHERE id = 1;" +
                "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)" +
                "VALUES (2 , 1, 200, 'for taxi', current_timestamp);");
         stat.executeUpdate("UPDATE account SET balance = balance - 150 WHERE id = 2;" +
                "UPDATE account SET balance = balance + 150 WHERE id = 1;" +
                "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)" +
                "VALUES (2 , 1, 150, 'for segoreti', current_timestamp);");
         stat.executeUpdate("UPDATE account SET balance = balance - 2500 WHERE id = 3;" +
                "UPDATE account SET balance = balance + 2500 WHERE id = 4;" +
                "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)" +
                "VALUES (3 , 4, 2500, 'za kvartiry', current_timestamp);");
         stat.executeUpdate("UPDATE account SET balance = balance - 400 WHERE id = 4;" +
                "UPDATE account SET balance = balance + 400 WHERE id = 2;" +
                "INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)" +
                "VALUES (4 , 2, 400, 'za lekarstva', current_timestamp);");
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
                "from_account_id integer REFERENCES account (id) on delete set null, " +
                "to_account_id integer REFERENCES account (id) on delete set null, " +
                "amount numeric NOT NULL, " +
                "transfer_date timestamp," +
                "comment varchar(255), " +
                "primary key (transfer_id))");
    }

    private void insertInitData(Statement stat) throws SQLException {
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Sergey Lebedev' , 500) " );
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Alexandr Smoleev' , 300000) " );
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Natalia Korneeva' , 12000) " );
        stat.executeUpdate("INSERT INTO account(client_name, balance)" +
                " VALUES ('Leonid Terekhov' , 6000) " );

    }

    private boolean selectTest(Statement stat) throws SQLException {
        boolean isResult = false;
        try (ResultSet result = stat.executeQuery("SELECT * FROM account"))
        {
            if (result.next()) {
                isResult = true;
            }
        } finally {
            return isResult;
        }
    }
}