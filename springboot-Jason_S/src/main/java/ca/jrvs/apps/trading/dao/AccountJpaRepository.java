package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<Account, Integer> {

  Account getAccountByTraderId(Integer traderId);

}
