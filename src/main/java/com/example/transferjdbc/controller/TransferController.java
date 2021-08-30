package com.example.transferjdbc.controller;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public Transfer doTransfer(@RequestBody Transfer transfer) {
        if (!transfer.getFrom_account_id().equals(transfer.getTo_account_id())
                && transfer.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            return transferService.transferMoney(transfer);
        }
        return null;
    }

    @GetMapping("history/{clientId}")
    public Iterable<Transfer> listTransfer(@PathVariable Long clientId){
        return transferService.findById(clientId);
    }
}
