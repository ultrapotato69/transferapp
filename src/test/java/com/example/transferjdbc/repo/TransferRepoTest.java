package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Transfer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferRepoTest {
    @Autowired
    TransferRepoRawJDBC transferRepo;

    @Test
    public void TransferTest() throws SQLException {
        Transfer transfer = transferRepo.transferMoney(new Transfer(null, 2L, 1L, new BigDecimal(200), "hi", null));
        Assert.assertEquals(transfer.getAmount(), new BigDecimal(200));
    }
}
