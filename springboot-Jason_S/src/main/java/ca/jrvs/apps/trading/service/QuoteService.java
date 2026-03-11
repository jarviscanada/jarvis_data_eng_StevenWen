package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.ResourceNotFoundException;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.dao.QuoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuoteService {
  /**
   * Find an IexQuote
   * @param symbol
   * @return IexQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
  @Autowired
  private MarketDataDao marketDataDao;
  @Autowired
  private QuoteDao quoteDao;

  public Quote findQuoteByTicker(String symbol) throws Exception {
    return marketDataDao.findById(symbol);
  }

  /**
   * Update quote table against source
   *
   * - get all quotes from the db
   * - for each ticker get Quote
   * - persist quote to db
   *
   * @throws ResourceNotFoundException if ticker is not found from IEX
   * @throws DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void updateMarketData() throws Exception {
    List<Quote> quotes = quoteDao.findAll();
    Set<String> tickers = new HashSet<>();
    for (Quote quote : quotes) {
        tickers.add(quote.getTicker());
    }

    for (String ticker: tickers) {
      Quote quoteToAdd = findQuoteByTicker(ticker);
      saveQuote(quoteToAdd);
    }

    return;
  }

  /**
   * Update singular ticker to quote table
   *
   * - get quote from given ticker
   * - persist quote to db
   *
   * @throws ResourceNotFoundException if ticker is not found from IEX
   * @throws DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public Quote inputQuoteByTicker(String ticker) throws Exception {

    System.out.println(quoteDao.findAll());
    Quote quoteToAdd = findQuoteByTicker(ticker);
    saveQuote(quoteToAdd);
    return quoteToAdd;

  }

  /**
   * Update a given quote to the quote table without validation
   *
   * @param quote entity to save
   * @return the saved quote entity
   */
  public Quote saveQuote(Quote quote) {
    quoteDao.save(quote); // Enforces Uniqueness because PK
    return quote;
  }

  public List<Quote> getDailyList() {
    return quoteDao.findAll();
  }
}
