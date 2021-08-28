package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.util.ConnectionToDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepo {
    private Connection connection;


    @Autowired
    public AccountRepo(ConnectionToDb connection) throws SQLException, IOException {
        this.connection = connection.getConnection();
    }

    public Account findByName(String client_name) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement("select id, client_name, balance from account where client_name = ?")) {
            statement.setString(1, client_name);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                account = new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("client_name"),
                        resultSet.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Iterable<Account> findAll() {

        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement( "select id, client_name, balance from account order by id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("client_name"),
                        resultSet.getBigDecimal("balance"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public Account findById(Long id) {
        Account account = null;
        try (PreparedStatement statement =
                     connection.prepareStatement("select id, client_name, balance from account where id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                account = new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("client_name"),
                        resultSet.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Long create(String client_name, BigDecimal balance) {
        long key = 0;
        try (PreparedStatement statement =
                     connection.prepareStatement( "INSERT INTO account(client_name, balance) VALUES (? , ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client_name);
            statement.setBigDecimal(2, balance);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getLong("id");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public boolean update(Long id, String client_name, BigDecimal balance) {
        boolean isInsert = false;
        try (PreparedStatement statement =
                     connection.prepareStatement( "UPDATE account SET client_name = ?, balance = ? WHERE id = ?")) {
            statement.setLong(3, id);
            statement.setString(1, client_name);
            statement.setBigDecimal(2, balance);
            int row = statement.executeUpdate();
            if (row > 0) {
                isInsert = true;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    public boolean delete(Long id) {
        boolean isDeleted = false;
        try (PreparedStatement statement =
                     connection.prepareStatement("delete from account where id = ?")) {
            statement.setLong(1, id);
            int row = statement.executeUpdate();

            if (row > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}