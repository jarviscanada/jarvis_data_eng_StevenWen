package ca.jrvs.apps.trading.Model;

import java.io.Serializable;
import java.util.Objects;

public class PositionId implements Serializable {

  private Integer accountId;
  private String ticker;

  public PositionId() {
  }

  public PositionId(Integer accountId, String ticker) {
    this.accountId = accountId;
    this.ticker = ticker;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccount_id(Integer accountId) {
    this.accountId = accountId;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PositionId)) return false;
    PositionId that = (PositionId) o;
    return Objects.equals(accountId, that.accountId) &&
        Objects.equals(ticker, that.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, ticker);
  }
}
