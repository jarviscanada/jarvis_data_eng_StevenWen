package ca.jrvs.apps.trading.Model;

import ca.jrvs.apps.trading.dao.TraderAccountView;

import java.util.List;

public class PortfolioView {
  private TraderAccountView traderAccountView;


  private List<Position> positions;

  public List<Position> getPositions() {
    return positions;
  }

  public void setPositions(List<Position> positions) {
    this.positions = positions;
  }

  public TraderAccountView getTraderAccountView() {
    return traderAccountView;
  }

  public void setTraderAccountView(TraderAccountView traderAccountView) {
    this.traderAccountView = traderAccountView;
  }
}
