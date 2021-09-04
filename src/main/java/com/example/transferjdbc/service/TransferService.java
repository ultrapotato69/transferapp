package com.example.transferjdbc.service;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.repo.AccountRepo;
import com.example.transferjdbc.repo.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransferService {

    private final TransferRepo transferRepo;

    @Autowired
    public TransferService(TransferRepo transferRepo, AccountRepo accountRepo) {
        this.transferRepo = transferRepo;
    }

    public Transfer transferMoney(Transfer transfer) {
        return transferRepo.transferMoney(transfer);
    }

    public Iterable<Transfer> findById(Long clientId) {
        return transferRepo.findAllTransfersById(clientId);
    }
}
