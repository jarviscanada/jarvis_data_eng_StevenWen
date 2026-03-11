package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.QuoteDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuoteDaoIntTest {
  @Autowired
  private QuoteDao quoteDao;
  private Quote testQuote;

  @Before
  public void init() {
    testQuote = new Quote();
    testQuote.setTicker("tester");
    testQuote.setLastPrice(1.0);
    testQuote.setAskPrice(3.0);
    testQuote.setBidPrice(2.0);
    testQuote.setAskSize(5);
    testQuote.setBidSize(6);
    quoteDao.save(testQuote);
  }

  @Test
  public void testInsert() {
    quoteDao.save(testQuote);
    assertTrue(quoteDao.existsById(testQuote.getTicker()));
  }

  @Test
  public void testDelete() {
    quoteDao.deleteById(testQuote.getTicker());
    assertFalse(quoteDao.existsById(testQuote.getTicker()));
  }
}
