package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.*;
import ca.jrvs.apps.trading.dao.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;



@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PositionDaoIntTest {

  @Autowired
  private PositionDao dao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountJpaRepository accountDao;

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  private Trader trader;
  private Account account;
  private SecurityOrder order;

  @Before
  public void init() {

    securityOrderDao.deleteAll();
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
    account.setAmount(10000.0);

    accountDao.save(account);

    Quote quote1 = new Quote();
    quote1.setTicker("AAPL");
    quote1.setLastPrice(150.0);
    quote1.setAskPrice(151.0);
    quote1.setAskSize(100);
    quote1.setBidPrice(149.0);
    quote1.setBidSize(120);

    quoteDao.save(quote1);

    order = new SecurityOrder();
    order.setAccount(account);
    order.setStatus("FILLED");
    order.setTicker("AAPL");
    order.setSize(50);
    order.setPrice(150.0f);
    order.setNotes("Test position");

    securityOrderDao.save(order);

    securityOrderDao.flush();
    accountDao.flush();
  }

  @Test
  public void getPosition() {


    Optional<Position> newPosition = dao.findById(new PositionId(order.getAccount().getId(), order.getTicker()));

    List<Position> positions = dao.findAll();
    List<SecurityOrder> orders = securityOrderDao.findAll();
    List<Account> accounts = accountDao.findAll();
    assertTrue(newPosition.isPresent());

    Position position = newPosition.get();

    assertEquals(account.getId(), position.getAccountId());
    assertEquals("AAPL", position.getTicker());
    assertEquals(Integer.valueOf(50), position.getPosition());
  }
}
