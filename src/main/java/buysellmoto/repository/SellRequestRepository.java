package buysellmoto.repository;

import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.entity.SellRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRequestRepository extends JpaRepository<SellRequestEntity, Long> {

}