package com.example.transferjdbc.repo.rawJdbc;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository("accountRepoRawJDBC")
@Primary
class AccountRepoRawJdbc implements AccountRepo {
      
    private Connection connection;


    @Autowired
    public AccountRepoRawJdbc(final DataSource dataSource) throws SQLException, IOException {
        this.connection = dataSource.getConnection();
    }

    @Override
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

    @Override
    public Optional<Account> findById(Long id) {
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
        return Optional.ofNullable(account);
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
        long key = 0;
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO account(client_name, balance) VALUES (?, ?) "
                             , PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getClient_name());
            statement.setBigDecimal(2, account.getBalance());
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
        account.setId(key);
        return account;
    }
    private Account update(Account account) {
        Account accountFromDB = null;
        try (PreparedStatement statement =
                     connection.prepareStatement("UPDATE account SET client_name = ?, balance = ? WHERE id = ?")) {
            statement.setLong(3, account.getId());
            statement.setString(1, account.getClient_name());
            statement.setBigDecimal(2, account.getBalance());
            int row = statement.executeUpdate();
            if (row > 0) {
                accountFromDB = this.findById(account.getId()).orElseThrow();
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch(NoSuchElementException e) {
            System.err.format("Do not find account id %s\n", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return accountFromDB;
        }
    }
    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement =
                     connection.prepareStatement("delete from account where id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}