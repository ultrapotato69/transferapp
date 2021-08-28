package com.example.transferjdbc.service;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.repo.AccountRepo;
import com.example.transferjdbc.repo.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final TransferRepo transferRepo;
    private final AccountRepo accountRepo;

    @Autowired
    public TransferService(TransferRepo transferRepo, AccountRepo accountRepo) {
        this.transferRepo = transferRepo;
        this.accountRepo = accountRepo;
    }

    public Transfer transferMoney(Transfer transfer) {
        if (
                !transfer.getFrom_account_id().equals(transfer.getTo_account_id())
                && transfer.getAmount().compareTo(BigDecimal.ZERO) > 0
                && accountRepo.findById(transfer.getFrom_account_id()).getBalance().compareTo(transfer.getAmount()) >= 0
        ) {
            return transferRepo.transferMoney(transfer.getFrom_account_id(), transfer.getTo_account_id(), transfer.getAmount(), transfer.getComment());
        }
        return null;
    }

    public Iterable<Transfer> findById(Long clientId) {
       return transferRepo.findById(clientId);
    }
}
