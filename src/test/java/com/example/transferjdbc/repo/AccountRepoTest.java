package com.example.transferjdbc.repo;

import com.example.transferjdbc.domain.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepoTest {

    @Autowired
    AccountRepo accountRepo;



    @Test
    public void saveTest() {
        Long id = accountRepo.create("Alexander Pushkin", new BigDecimal(900));
        Assert.assertNotNull(id);
    }

    @Test
    public void listTest() {
        List<Account> list = (List<Account>) accountRepo.findAll();
        boolean isMatch = list.stream().anyMatch(x -> x.getClient_name().equals("Fyodor Dostoevsky"));
        Assert.assertTrue(isMatch);
    }
}
