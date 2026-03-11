package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccountDaoIntTest {

  @Autowired
  private AccountJpaRepository dao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private  SecurityOrderDao securityOrderDao;

  private Account account;
  private Trader trader;

  @Before
  public void init() {

    securityOrderDao.deleteAll();
    dao.deleteAll();
    traderDao.deleteAll();

    trader = new Trader();
    trader.setFirst_name("Tester");
    trader.setLast_name("John");
    trader.setEmail("testerjohn@gmail.com");
    trader.setCountry("Canada");
    trader.setDob(LocalDate.now());

    traderDao.save(trader);



  }

  @Test
  public void insertGetAccount() {

    account = new Account();
    account.setTrader(trader);
    account.setAmount(1000.0);
    dao.save(account);
    Optional<Account> newAccount = dao.findById(account.getId());
    assertTrue(newAccount.isPresent());

    Account savedAccount = newAccount.get();

    assertEquals(account.getId(), savedAccount.getId());
    assertEquals(account.getAmount(), savedAccount.getAmount());
    assertEquals(account.getTrader().getId(), savedAccount.getTrader().getId());

    dao.deleteById(savedAccount.getId());

    Optional<Account> afterDelete = dao.findById(savedAccount.getId());
    assertFalse(afterDelete.isPresent());
  }

  @After
  public void cleanup() {
    dao.deleteAll();
    traderDao.deleteAll();
  }
}
