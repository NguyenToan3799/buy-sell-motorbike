package buysellmoto.repository;

import buysellmoto.model.entity.CheckingAppointmentEntity;
import buysellmoto.model.entity.RejectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAppointmentRepository extends JpaRepository<CheckingAppointmentEntity, Long> {

}