package com.example.transferjdbc.controller;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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
        final long then = System.nanoTime();
        Transfer doingTransfer = transferService.transferMoney(transfer);
        final long nanos = TimeUnit.NANOSECONDS.toNanos(System.nanoTime() - then);
        System.out.println("Transfer is: " + nanos);
        return doingTransfer;
    }

    @GetMapping("history/{clientId}")
    public Iterable<Transfer> listTransfer(@PathVariable Long clientId){
        return transferService.findById(clientId);
    }
}
