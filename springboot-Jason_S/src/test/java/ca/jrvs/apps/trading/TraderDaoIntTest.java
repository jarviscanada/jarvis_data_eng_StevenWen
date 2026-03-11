package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.TraderDao;
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
public class TraderDaoIntTest {
  @Autowired
  private TraderDao dao;
  private Trader trader;

  @Before
  public void init() {
    trader = new Trader();
    trader.setFirst_name("Tester");
    trader.setLast_name("John");
    trader.setEmail("testerjohn@gmail.com");
    trader.setCountry("Canada");
    trader.setDob(LocalDate.now());

    dao.deleteAll();
  }

  @Test
  public void insertGetTrader() {
    dao.save(trader);

    Optional<Trader> newTrader = dao.findById(trader.getId());
    assertTrue(newTrader.isPresent());

    Trader savedTrader = newTrader.get();

    assertEquals(trader.getId(), savedTrader.getId());
    assertEquals(trader.getFirst_name(), savedTrader.getFirst_name());
    assertEquals(trader.getLast_name(), savedTrader.getLast_name());
    assertEquals(trader.getEmail(), savedTrader.getEmail());
    assertEquals(trader.getCountry(), savedTrader.getCountry());
    assertEquals(trader.getDob(), savedTrader.getDob());

    dao.deleteById(savedTrader.getId());

    Optional<Trader> afterDelete = dao.findById(savedTrader.getId());
    assertFalse(afterDelete.isPresent());
  }
}
