package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.service.QuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuoteServiceIntTest {
  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  private Quote testQuote;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Before
  public void setup() {

    securityOrderDao.deleteAll();
    quoteDao.deleteAll();

    testQuote = new Quote();
    testQuote.setTicker("AAPL");
    testQuote.setLastPrice(1.0);
    testQuote.setAskPrice(3.0);
    testQuote.setBidPrice(2.0);
    testQuote.setAskSize(5);
    testQuote.setBidSize(6);
    quoteDao.save(testQuote);
  }

  @Test
  public void testFindQuoteByTicker() throws Exception {
    Quote quote = quoteService.findQuoteByTicker(testQuote.getTicker());
    assertEquals(testQuote.getTicker(), quote.getTicker());
  }

  @Test
  public void testUpdateMarketData() throws Exception {
    quoteService.saveQuote(testQuote);
    quoteService.updateMarketData();
    Optional<Quote> updatedQuote = quoteDao.findById(testQuote.getTicker());
    assertTrue(updatedQuote.isPresent());

    Quote result = updatedQuote.get();
    assertNotNull(result.getLastPrice());
  }


  @Test
  public void testSaveQuote() {
    quoteService.saveQuote(testQuote);
    Optional<Quote> updatedQuote = quoteDao.findById(testQuote.getTicker());
    assertTrue(updatedQuote.isPresent());
  }

  @Test
  public void testInputQuoteByTicker() throws Exception {
    quoteService.inputQuoteByTicker("AAPL");
    Optional<Quote> updatedQuote = quoteDao.findById("AAPL");
    assertTrue(updatedQuote.isPresent());
    Quote result = updatedQuote.get();
    assertNotNull(result.getLastPrice());
  }
}
