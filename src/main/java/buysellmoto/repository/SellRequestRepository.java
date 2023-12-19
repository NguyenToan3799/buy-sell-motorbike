package buysellmoto.repository;

import buysellmoto.model.entity.SellRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellRequestRepository extends JpaRepository<SellRequestEntity, Long> {

    List<SellRequestEntity> findAllByShowroomIdAndStatus(Long showroomId, String status);

    List<SellRequestEntity> findAllByStatus(String status);

    List<SellRequestEntity> findAllByIdIn(List<Long> ids);

    List<SellRequestEntity> findAllByCustomerId(Long customerId);

}