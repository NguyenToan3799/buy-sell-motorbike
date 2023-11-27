package buysellmoto.repository;

import buysellmoto.model.entity.CheckedSellRequestEntity;
import buysellmoto.model.entity.PurchaseAppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedSellRequestRepository extends JpaRepository<CheckedSellRequestEntity, Long> {

    CheckedSellRequestEntity findBySellRequestId(Long sellRequestId);

}