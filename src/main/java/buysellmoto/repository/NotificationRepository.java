package buysellmoto.repository;

import buysellmoto.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByCustomerId(Long customerId);

    Optional<NotificationEntity> findByCustomerIdAndIsNotified(Long customerId, boolean isNotified);

}