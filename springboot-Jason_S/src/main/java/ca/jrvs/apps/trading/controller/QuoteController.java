package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.Model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  private static final Logger log = LoggerFactory.getLogger(QuoteController.class);
  private QuoteService quoteService;

  @Autowired
  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @GetMapping(path = "/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote getQuote(@PathVariable String ticker) throws Exception {
    Quote quote = quoteService.findQuoteByTicker(ticker);
    System.out.println(quote.toString());
    return quote;
  }

  @PutMapping(path = "/FinnAPI/")
  @ResponseStatus(HttpStatus.OK)
  public void updateMarketData() throws Exception {
      quoteService.updateMarketData();
  }

  @PostMapping(path = "/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote inputQuoteByTicker(@PathVariable String ticker) throws Exception {
    return quoteService.inputQuoteByTicker(ticker);
  }

  @PutMapping(path = "")
  @ResponseStatus(HttpStatus.OK)
  public void putQuote(@RequestBody Quote quote) {
    quoteService.saveQuote(quote);
  }

  @GetMapping(path="/dailyList")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> getDailyList() {
    return quoteService.getDailyList();
  }
}
