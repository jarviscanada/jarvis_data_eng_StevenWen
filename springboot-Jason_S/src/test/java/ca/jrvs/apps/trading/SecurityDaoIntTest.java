package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.Model.SecurityOrder;
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
public class SecurityDaoIntTest {

  @Autowired
  private SecurityOrderDao dao;

  @Autowired
  private AccountJpaRepository accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder securityOrder;
  private Account account;
  private Trader trader;

  @Before
  public void init() {

    dao.deleteAll();
    quoteDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();



    trader = new Trader();
    trader.setFirst_name("Tester");
    trader.setLast_name("John");
    trader.setEmail("testerjohn@gmail.com");
    trader.setCountry("Canada");
    trader.setDob(LocalDate.now());

    traderDao.save(trader);

    account = new Account();
    account.setTrader(trader);
    account.setAmount(5000.0);

    accountDao.save(account);

    Quote quote1 = new Quote();
    quote1.setTicker("AAPL");
    quote1.setLastPrice(150.0);
    quote1.setAskPrice(151.0);
    quote1.setAskSize(100);
    quote1.setBidPrice(149.0);
    quote1.setBidSize(120);
    quoteDao.save(quote1);

    securityOrder = new SecurityOrder();
    securityOrder.setAccount(account);
    securityOrder.setStatus("OPEN");
    securityOrder.setTicker("AAPL");
    securityOrder.setSize(10);
    securityOrder.setPrice(150.5f);
    securityOrder.setNotes("Test order");
  }

  @Test
  public void insertGetSecurityOrder() {

    dao.save(securityOrder);

    Optional<SecurityOrder> newOrder = dao.findById(securityOrder.getId());
    assertTrue(newOrder.isPresent());

    SecurityOrder savedOrder = newOrder.get();

    assertEquals(securityOrder.getId(), savedOrder.getId());
    assertEquals(securityOrder.getStatus(), savedOrder.getStatus());
    assertEquals(securityOrder.getTicker(), savedOrder.getTicker());
    assertEquals(securityOrder.getSize(), savedOrder.getSize());
    assertEquals(securityOrder.getPrice(), savedOrder.getPrice());
    assertEquals(securityOrder.getNotes(), savedOrder.getNotes());
    assertEquals(securityOrder.getAccount().getId(), savedOrder.getAccount().getId());

    dao.deleteById(savedOrder.getId());

    Optional<SecurityOrder> afterDelete = dao.findById(savedOrder.getId());
    assertFalse(afterDelete.isPresent());
  }

  @After
  public void cleanup() {
    dao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();

  }
}
