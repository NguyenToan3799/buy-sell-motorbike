package buysellmoto.repository;

import buysellmoto.model.entity.BuyRequestEntity;
import buysellmoto.model.entity.RejectRequestEntity;
import buysellmoto.model.entity.SellRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequestEntity, Long> {

    List<BuyRequestEntity> findAllByShowroomIdAndStatus(Long showroomId, String status);

    List<BuyRequestEntity> findAllByIdIn(List<Long> ids);

    List<BuyRequestEntity> findAllByCustomerId(Long customerId);

    List<BuyRequestEntity> findAllByPostIdAndStatus(Long postId, String status);
}