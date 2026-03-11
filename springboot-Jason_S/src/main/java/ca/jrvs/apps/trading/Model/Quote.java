package ca.jrvs.apps.trading.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@JsonIgnoreProperties({"timestamp"})
@Entity
@Table(name = "quote")
public class Quote {

  @Id
  @Column(name = "ticker", nullable = false, unique = true)
  @JsonProperty("symbol")
  private String ticker; //primary key
  private Double lastPrice;
  @JsonProperty("bid")
  private Double bidPrice;
  @JsonProperty("bsize")
  private Integer bidSize;
  @JsonProperty("ask")
  private Double askPrice;
  @JsonProperty("asize")
  private Integer askSize;

  //getters and setters

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }
  @JsonProperty
  public Double getLastPrice() {
    return lastPrice;
  }
  @JsonIgnore
  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Integer getBidSize() {
    return bidSize;
  }

  public void setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
  }

  public Integer getAskSize() {
    return askSize;
  }

  public void setAskSize(Integer askSize) {
    this.askSize = askSize;
  }

  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }
}
