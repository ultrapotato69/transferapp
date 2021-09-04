package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.util.InitDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferRepoTest {
    @Autowired
    TransferRepo transferRepo;
    @Autowired
    InitDB initDB;

    @Before
    public void init() throws SQLException {
        initDB.init();
    }

    @Test
    public void doTransferTest() throws SQLException {
        Transfer transfer = transferRepo.transferMoney(new Transfer(null, 2L, 1L, new BigDecimal(200), "hi", null));
        Assert.assertEquals(transfer.getAmount(), new BigDecimal(200));
    }

    @Test
    public void transferListTest() throws SQLException {
        List<Transfer> transferList = (List<Transfer>) transferRepo.findAllTransfersById(1L);
        Assert.assertFalse(transferList.isEmpty());
    }
    @Test
    public void EmptyTransferListTest() throws SQLException {
        List<Transfer> transferList = (List<Transfer>) transferRepo.findAllTransfersById(59578L);
        Assert.assertTrue(transferList.isEmpty());
    }
}
