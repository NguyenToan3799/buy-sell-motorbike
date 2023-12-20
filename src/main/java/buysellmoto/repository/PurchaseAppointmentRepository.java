package buysellmoto.repository;

import buysellmoto.model.entity.PurchaseAppointmentEntity;
import buysellmoto.model.entity.SellAgreementContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseAppointmentRepository extends JpaRepository<PurchaseAppointmentEntity, Long> {

    Optional<PurchaseAppointmentEntity> findByMotorbikeId(Long motorbikeId);

}