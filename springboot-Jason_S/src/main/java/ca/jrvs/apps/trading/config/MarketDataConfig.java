package ca.jrvs.apps.trading.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="")
public class MarketDataConfig {

  private String finnhub_host;
  private String finnhub_token;
  private String finage_host;
  private String finage_token;

  public String getFinnhub_host() {
    return finnhub_host;
  }

  public void setFinnhub_host(String finnhub_host) {
    this.finnhub_host = finnhub_host;
  }

  public String getFinnhub_token() {
    return finnhub_token;
  }

  public void setFinnhub_token(String finnhub_token) {
    this.finnhub_token = finnhub_token;
  }

  public String getFinage_host() {
    return finage_host;
  }

  public void setFinage_host(String finage_host) {
    this.finage_host = finage_host;
  }

  public String getFinage_token() {
    return finage_token;
  }

  public void setFinage_token(String finage_token) {
    this.finage_token = finage_token;
  }
}
