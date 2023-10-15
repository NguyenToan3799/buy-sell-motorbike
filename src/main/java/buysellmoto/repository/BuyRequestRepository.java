package buysellmoto.repository;

import buysellmoto.model.entity.BuyRequestEntity;
import buysellmoto.model.entity.RejectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequestEntity, Long> {

}