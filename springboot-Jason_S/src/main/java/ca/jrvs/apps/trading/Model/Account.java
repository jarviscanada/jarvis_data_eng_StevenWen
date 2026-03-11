package ca.jrvs.apps.trading.Model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "trader_id", nullable = false)
  private Trader trader;
  private Double amount;

  public Integer getId() {
    return id;
  }

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
