package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.PortfolioView;
import ca.jrvs.apps.trading.Model.Position;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.AccountJpaRepository;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.dao.TraderDao;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

  @Autowired
  AccountJpaRepository accountDao;

  @Autowired
  PositionDao positionDao;

  /**
   * Create and return a traderAccountView by trader ID
   * - get trader account by id
   * - get trader info by id
   * - create and return a traderAccountView
   *
   * @param traderId must not be null
   * @return traderAccountView
   * @throws IllegalArgumentException if traderId is null or not found
   */
  public TraderAccountView getTraderAccount(Integer traderId) {
    Account account = findAccountByTraderId(traderId);
    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setAccount(findAccountByTraderId(traderId));
    traderAccountView.setTrader(account.getTrader());
    return traderAccountView;
  }

  /**
   * Create and return portfolioView by trader ID
   * - get account by trader id
   * - get positions by account id
   * - create and return a portfolioView
   *
   * @param traderId must not be null
   * @return portfolioView
   * @throws IllegalArgumentException if traderId is null or not found
   */
  public PortfolioView getProfileViewByTraderId(Integer traderId) {
    PortfolioView portfolioView = new PortfolioView();
    portfolioView.setTraderAccountView(getTraderAccount(traderId));
    Integer accountId = portfolioView.getTraderAccountView().getAccount().getId();
    portfolioView.setPositions(positionDao.findAllByAccountId(accountId));
    return portfolioView;
  }

  /**
   * Helper method to find a trader's corresponding account
   *
   * @param traderId
   * @return
   * @throws IllegalArgumentException if traderId is not found
   */
  private Account findAccountByTraderId(Integer traderId) {
    Account account = accountDao.getAccountByTraderId(traderId);
    if (account != null) {
      return account;
    }
    else {
      throw new IllegalArgumentException("Invalid Trader ID");
    }
  }
}
