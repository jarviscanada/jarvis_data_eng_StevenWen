package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.Model.SecurityOrder;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.service.TraderService;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TraderServiceIntTest {

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private AccountJpaRepository accountDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private QuoteDao quoteDao;

  @Autowired
  private TraderService traderService;

  private Trader trader;
  private Account account;
  private SecurityOrder order1;
  private SecurityOrder order2;

  @Before
  public void init() {
    securityOrderDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();

    trader = new Trader();
    trader.setFirst_name("Tester");
    trader.setLast_name("John");
    trader.setEmail("testerjohn@gmail.com");
    trader.setCountry("Canada");
    trader.setDob(LocalDate.of(1990, 1, 1));

    trader = traderDao.save(trader);

    account = new Account();
    account.setTrader(trader);
    account.setAmount(0.0);
    account = accountDao.save(account);

    Quote quote1 = new Quote();
    quote1.setTicker("AAPL");
    quote1.setLastPrice(150.0);
    quote1.setAskPrice(151.0);
    quote1.setAskSize(100);
    quote1.setBidPrice(149.0);
    quote1.setBidSize(120);

    Quote quote2 = new Quote();
    quote2.setTicker("GOOG");
    quote2.setLastPrice(2800.0);
    quote2.setAskPrice(2810.0);
    quote2.setAskSize(50);
    quote2.setBidPrice(2795.0);
    quote2.setBidSize(70);

    quoteDao.save(quote1);
    quoteDao.save(quote2);

    order1 = new SecurityOrder();
    order1.setAccount(account);
    order1.setTicker("AAPL");
    order1.setSize(10);
    order1.setPrice(150.0f);
    order1.setStatus("OPEN");

    order2 = new SecurityOrder();
    order2.setAccount(account);
    order2.setTicker("GOOG");
    order2.setSize(5);
    order2.setPrice(2800.0f);
    order2.setStatus("OPEN");



    securityOrderDao.save(order1);
    securityOrderDao.save(order2);
  }

  @Test
  public void testSetup() {
    List<SecurityOrder> orders = securityOrderDao.findByAccount_id(account.getId());
    assert orders.size() == 2;
    assert trader.getId() != null;
    assert account.getId() != null;
  }

  @Test
  public void testCreateTraderAndAccount() {
    Trader newTrader = new Trader();
    newTrader.setDob(LocalDate.now());
    newTrader.setCountry("Canada");
    newTrader.setEmail("tradertester123@gmail.com");
    newTrader.setFirst_name("trader");
    newTrader.setLast_name("tester");

    traderService.createTraderAndAccount(newTrader);
    assertTrue(traderDao.findById(newTrader.getId()).isPresent());
    assertNotNull(accountDao.getAccountByTraderId(newTrader.getId()));
  }



  @Test
  public void testDepositAndWithdraw() {
    traderService.deposit(trader.getId(), 1000.0);
    Double new_amount = account.getAmount() + 1000;
    assertEquals(accountDao.getAccountByTraderId(trader.getId()).getAmount(), new_amount);

    traderService.withdraw(trader.getId(), 500.0);
    new_amount = new_amount - 500;
    assertEquals(accountDao.getAccountByTraderId(trader.getId()).getAmount(), new_amount);

  }


  @Test public void testDeleteTraderById() {
    traderService.deleteTraderById(trader.getId());
    Optional<Account> afterDeleteAccount = accountDao.findById(account.getId());
    assertFalse(afterDeleteAccount.isPresent());
    Optional<Trader> afterDeleteTrader = traderDao.findById(trader.getId());
    assertFalse(afterDeleteTrader.isPresent());
    Optional<SecurityOrder> afterDeleteSecurity = securityOrderDao.findById(order1.getId());
    assertFalse(afterDeleteSecurity.isPresent());
  }

}
