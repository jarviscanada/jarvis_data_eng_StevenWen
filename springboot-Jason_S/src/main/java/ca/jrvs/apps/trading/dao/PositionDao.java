package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.Model.Position;
import ca.jrvs.apps.trading.Model.PositionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionDao extends JpaRepository<Position, PositionId> {
  List<Position> findAllByAccountId(Integer AccountId);
}
