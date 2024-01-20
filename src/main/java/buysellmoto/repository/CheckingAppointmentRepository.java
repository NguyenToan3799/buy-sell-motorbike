package buysellmoto.repository;

import buysellmoto.core.enumeration.CheckingAppointmentEnum;
import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.entity.CheckingAppointmentEntity;
import buysellmoto.model.entity.RejectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingAppointmentRepository extends JpaRepository<CheckingAppointmentEntity, Long> {

    List<CheckingAppointmentEntity> findAllByShowroomIdAndStatus(Long showroomId, String status);

    List<CheckingAppointmentEntity> findAllByStatus(String status);

    Optional<CheckingAppointmentEntity> findByBuyRequestIdAndStatus(Long buyRequestId, String status);

}