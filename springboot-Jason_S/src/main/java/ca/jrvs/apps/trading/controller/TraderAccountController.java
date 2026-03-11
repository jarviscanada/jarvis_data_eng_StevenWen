package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.Account;
import ca.jrvs.apps.trading.Model.Trader;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/trader")
public class TraderAccountController
{
  @Autowired
  TraderService traderService;

  @PostMapping(path= "")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public TraderAccountView createTrader(@RequestBody Trader trader){
    return traderService.createTraderAndAccount(trader);
  }

  @PostMapping(path="/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public TraderAccountView createTrader(
      @PathVariable String firstname,
      @PathVariable String lastname,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
      @PathVariable String country,
      @PathVariable String email){
    Trader newTrader = new Trader();
    newTrader.setFirst_name(firstname);
    newTrader.setEmail(email);
    newTrader.setLast_name(lastname);
    newTrader.setCountry(country);
    newTrader.setDob(dob);
    return traderService.createTraderAndAccount(newTrader);
  }

  @DeleteMapping(path="/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTrader(@PathVariable Integer traderId) {
    traderService.deleteTraderById(traderId);
  }

  @PutMapping(path="/deposit/traderId/{traderId}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account depositFund(@PathVariable Integer traderId, @PathVariable Double amount){
    return traderService.deposit(traderId, amount);
  }

  @PutMapping(path="/withdraw/traderId/{traderId}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account withdrawFund(@PathVariable Integer traderId, @PathVariable  Double amount){
    return traderService.withdraw(traderId, amount);
  }
}
