package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Transfer;

public interface TransferRepo {
    Transfer transferMoney(Transfer transfer);
    Iterable<Transfer> findAllTransfersById(Long clientId);
}
