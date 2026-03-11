package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.Model.*;
import ca.jrvs.apps.trading.dao.AccountJpaRepository;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

  @Autowired
  AccountJpaRepository accountDao;

  @Autowired
  QuoteDao quoteDao;

  @Autowired
  PositionDao positionDao;

  @Autowired
  SecurityOrderDao securityOrderDao;

  /**
   * Execute a market order
   * - validate the order (e.g. size and ticker)
   * - create a securityOrder
   * - handle buy or sell orders
   * 	- buy order : check account balance
   * 	- sell order : check position for the ticker/symbol
   * 	- do not forget to update the securityOrder.status
   * - save and return securityOrder
   *
   * NOTE: you are encouraged to make some helper methods (protected or private)
   *
   * @param orderData market order
   * @return SecurityOrder from security_order table
   * @throws DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid inputs
   */
  public SecurityOrder executeMarketOrder(MarketOrder orderData) {
    Account account;
    Quote quote;

    if (orderData.getSize() <= 0) {
      throw new IllegalArgumentException(("Invalid Size"));
    }

    try {
      account = accountDao.getAccountByTraderId(orderData.getTraderId());
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Invalid Trader ID");
    }

    Optional<Quote> optionalQuote = quoteDao.findById(orderData.getTicker());

    if (optionalQuote.isEmpty()) {
      throw new IllegalArgumentException("Invalid Ticker");
    }

    quote = optionalQuote.get();

    if (orderData.getOption() == MarketOrder.Option.BUY){
      if (account.getAmount() < orderData.getSize() * quote.getAskPrice() ){
        throw new IllegalArgumentException("Invalid Buy, not enough funds");
      } else if (orderData.getSize() > quote.getAskSize()) {
        throw new IllegalArgumentException("Invalid Buy, not enough Asker");

      }
      else {
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount(account);
        securityOrder.setTicker(orderData.getTicker());
        securityOrder.setNotes("");
        securityOrder.setSize(orderData.getSize());
        securityOrder.setPrice(quote.getAskPrice().floatValue());
        securityOrder.setStatus("FILLED");
        handleBuyMarketOrder(securityOrder, account);
        return securityOrder;
      }
    }

    else {
      // Selling so have to check whether have enought shared through Position

      PositionId positionId = new PositionId(account.getId(), quote.getTicker());
      Optional<Position> optionalPosition = positionDao.findById(positionId);
      if (optionalPosition.isEmpty()) {
        throw new IllegalArgumentException("Invalid Sell, not enough stocks");
      }
      Position position = optionalPosition.get();
      if (position.getPosition() < orderData.getSize()){
        throw new IllegalArgumentException("Invalid Sell, not enough stocks");
      } else if (orderData.getSize() > quote.getBidSize()) {
        throw new IllegalArgumentException("Invalid Sell, not enough bidder");
      } else {
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount(account);
        securityOrder.setTicker(orderData.getTicker());
        securityOrder.setNotes("");
        securityOrder.setSize(-orderData.getSize());
        securityOrder.setPrice(quote.getBidPrice().floatValue());
        securityOrder.setStatus("FILLED");
        handleSellMarketOrder(securityOrder, account);
        return securityOrder;
      }
    }
  }

  /**
   * Helper method to execute a buy order
   *
   * @param securityOrder to be saved in database
   * @param account account
   */
  protected void handleBuyMarketOrder(SecurityOrder securityOrder, Account account) {
    Double new_amount = account.getAmount() - securityOrder.getPrice() * securityOrder.getSize();
    account.setAmount(new_amount);
    accountDao.save(account);
    securityOrderDao.save(securityOrder);
  }

  /**
   * Helper method to execute a sell order
   *
   * @param securityOrder to be saved in database
   * @param account account
   */
  protected void handleSellMarketOrder(SecurityOrder securityOrder, Account account) {
    Double new_amount = account.getAmount() - (securityOrder.getSize() * securityOrder.getPrice());
    account.setAmount(new_amount);
    accountDao.save(account);
    securityOrderDao.save(securityOrder);
  }
}
