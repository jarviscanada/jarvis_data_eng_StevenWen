package ca.jrvs.apps.trading.dao;
import ca.jrvs.apps.trading.Model.Quote;
import ca.jrvs.apps.trading.ResourceNotFoundException;
import ca.jrvs.apps.trading.config.MarketDataConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Repository
public class MarketDataDao {


  public void setMarketDataConfig(MarketDataConfig marketDataConfig) {
    this.marketDataConfig = marketDataConfig;
  }

  @Autowired
  private MarketDataConfig marketDataConfig;

  /**
   * Get an IexQuote
   *
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  public Quote findById(String ticker) {
    if (ticker == null || ticker.trim().isEmpty()) {
      throw new IllegalArgumentException("Ticker cannot be null or empty");
    }

    String token = marketDataConfig.getFinage_token();
    String url = marketDataConfig.getFinage_host() + ticker.toUpperCase() + "?apikey=" + token;

    String finn_token = marketDataConfig.getFinnhub_token();
    String finn_url = marketDataConfig.getFinnhub_host() + "?symbol=" + ticker.toUpperCase() + "&token=" + finn_token;

    Optional<String> responseBody = executeHttpGet(url);

    if (responseBody.isEmpty()) {
      throw new DataRetrievalFailureException("Failed to parse response");
    }

    try {
      ObjectMapper mapper = new ObjectMapper();
      if (mapper.readTree(responseBody.get()).has("error")) {
        throw new ResourceNotFoundException("Ticker not found: " + ticker);
      }
      Quote quote = mapper.readValue(responseBody.get(), Quote.class);
      Optional<String> finnResponseBody = executeHttpGet(finn_url);

      if (finnResponseBody.isEmpty()) {
        throw new DataRetrievalFailureException("Failed to parse response");
      }
      try {
        String json = finnResponseBody.get();
        Double last_price = mapper.readTree(json).get("c").asDouble();
        quote.setLastPrice(last_price);
        return quote;
      }
      catch (Exception e) {
        throw new DataRetrievalFailureException("Failed to parse response", e);
      }

    } catch (Exception e) {
      throw new DataRetrievalFailureException("Failed to parse response", e);
    }

  }


  /**
   * Execute a GET request and return http entity/body as a string
   * Tip: use EntitiyUtils.toString to process HTTP entity
   *
   * @param url resource URL
   * @return http response body or Optional.empty for 404 response
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private static Optional<String> executeHttpGet(String url) {

    HttpClient client = getHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
    System.out.println(request);
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      int statusCode = response.statusCode();
      System.out.println(statusCode);
      if (statusCode == 200) {
        String body = response.body();
        return Optional.of(body);

      } else if (statusCode == 404) {
        return Optional.empty();

      } else {
        throw new DataRetrievalFailureException(
            "Unexpected HTTP status: " + statusCode
        );
      }

    } catch (Exception e) {
      throw new DataRetrievalFailureException("HTTP request failed", e);
    }
  }

  /**
   * Borrow a HTTP client from the HttpClientConnectionManager
   * @return a HttpClient
   */
  private static HttpClient getHttpClient() {
    return HttpClient.newHttpClient();
  }

}
