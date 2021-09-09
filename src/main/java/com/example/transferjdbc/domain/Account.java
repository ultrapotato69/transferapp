package com.example.transferjdbc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("account")
public class Account {

    @Id
    private Long id;
    private String client_name;
    private BigDecimal balance;

    public Account(Long id, String client_name, BigDecimal balance) {
        this.id = id;
        this.client_name = client_name;
        this.balance = balance;
    }

    public Account() {
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(client_name, account.client_name) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client_name, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client_name='" + client_name + '\'' +
                ", balance=" + balance +
                '}';
    }
}