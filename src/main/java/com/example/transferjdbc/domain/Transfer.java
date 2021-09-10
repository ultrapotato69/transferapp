package com.example.transferjdbc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transfer {
    private Long transfer_id;
    private Long from_account_id;
    private Long to_account_id;
    private BigDecimal amount;
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transfer_date;


    public Transfer(Long transfer_id, Long from_account_id, Long to_account_id, BigDecimal amount, String comment, LocalDateTime transfer_date) {
        this.transfer_id = transfer_id;
        this.from_account_id = from_account_id;
        this.to_account_id = to_account_id;
        this.amount = amount;
        this.comment = comment;
        this.transfer_date = transfer_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public Long getFrom_account_id() {
        return from_account_id;
    }

    public void setFrom_account_id(Long from_account_id) {
        this.from_account_id = from_account_id;
    }

    public Long getTo_account_id() {
        return to_account_id;
    }

    public void setTo_account_id(Long to_account_id) {
        this.to_account_id = to_account_id;
    }

    public LocalDateTime getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(LocalDateTime transfer_date) {
        this.transfer_date = transfer_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(transfer_id, transfer.transfer_id) && Objects.equals(from_account_id, transfer.from_account_id) && Objects.equals(to_account_id, transfer.to_account_id) && Objects.equals(amount, transfer.amount) && Objects.equals(transfer_date, transfer.transfer_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transfer_id, from_account_id, to_account_id, amount, transfer_date);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + transfer_id +
                ", from_account_id=" + from_account_id +
                ", to_account_id=" + to_account_id +
                ", amount=" + amount +
                ", transfer_date=" + transfer_date +
                '}';
    }
}