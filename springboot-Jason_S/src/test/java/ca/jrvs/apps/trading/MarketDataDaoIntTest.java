package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.Model.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MarketDataDaoIntTest {
  @Autowired
  private MarketDataDao dao;

  @Before
  public void init() {
  }

  @Test
  public void getQuote() {
    Quote quote = dao.findById("AAPL");
    assertEquals("AAPL", quote.getTicker());

  }
}
