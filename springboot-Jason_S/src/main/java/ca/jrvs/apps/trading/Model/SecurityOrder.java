package ca.jrvs.apps.trading.Model;

import javax.persistence.*;

@Entity
@Table(name ="security_order")
public class SecurityOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne(optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;
  private String status;
  private String ticker;
  private Integer size;
  private Float price;
  private String notes;

  public Integer getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void set() {
  }
}
