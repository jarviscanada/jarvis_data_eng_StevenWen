package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.Model.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityOrderDao extends JpaRepository<SecurityOrder, Integer> {
  List<SecurityOrder> findByAccount_id(Integer id);
}
