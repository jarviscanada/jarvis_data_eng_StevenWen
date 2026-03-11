package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.PortfolioView;
import ca.jrvs.apps.trading.dao.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;


  @GetMapping("/profile/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public TraderAccountView getAccount(Integer traderId) {
    return dashboardService.getTraderAccount(traderId);
  }

  @GetMapping("/portfolio/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public PortfolioView getPortfolioView(Integer traderId) {
    return dashboardService.getProfileViewByTraderId(traderId);
  }
}
