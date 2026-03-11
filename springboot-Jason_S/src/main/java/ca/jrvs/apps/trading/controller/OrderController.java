package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.Model.MarketOrder;
import ca.jrvs.apps.trading.Model.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/marketorder")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody()
  public SecurityOrder postMarketOrder(@RequestBody MarketOrder marketOrder){
    return orderService.executeMarketOrder(marketOrder);
  }
}
