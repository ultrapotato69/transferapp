package com.example.transferjdbc.repo;

import com.example.transferjdbc.config.SpringJdbcConfig;
import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.util.InitDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringJdbcConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AccountRepoTest {

    @Autowired
    AccountRepo accountRepo;
    @Autowired
    InitDB initDB;

    @Before
    public void init() throws SQLException {
        initDB.init();
    }

    @Test
    public void listTest() {
        List<Account> list = (List<Account>) accountRepo.findAll();
        boolean isMatch = list.stream().anyMatch(x -> x.getClient_name().equals("Sergey Lebedev"));
        Assert.assertTrue(isMatch);
    }

    @Test
    public void findOneTest() {
        Account account =  accountRepo.findById(1L).orElseThrow();
        Assert.assertNotNull(account);
    }

    @Test
    public void createTest() {
        Account account = accountRepo.save(
                new Account(null, "Alexander Pushkin", new BigDecimal(900)));
        Assert.assertNotNull(account.getId());
    }

    @Test
    public void updateTest() {
        Account account = accountRepo.save(
                new Account(2L, "Mikhail Lermontov", new BigDecimal(900)));
        Assert.assertEquals("Mikhail Lermontov", account.getClient_name());
    }

    @Test
    public void deleteByIdTest() {
        accountRepo.deleteById(3L);
        Assert.assertTrue(accountRepo.findById(3L).isEmpty());
    }


}
