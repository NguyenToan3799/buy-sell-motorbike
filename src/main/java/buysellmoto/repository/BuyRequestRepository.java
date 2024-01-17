package buysellmoto.repository;

import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.entity.BuyRequestEntity;
import buysellmoto.model.entity.RejectRequestEntity;
import buysellmoto.model.entity.SellRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequestEntity, Long> {

    List<BuyRequestEntity> findAllByShowroomIdAndStatus(Long showroomId, String status);

    List<BuyRequestEntity> findAllByIdIn(List<Long> ids);

    List<BuyRequestEntity> findAllByCustomerId(Long customerId);

    List<BuyRequestEntity> findAllByPostIdAndStatus(Long postId, String status);

    BuyRequestEntity findByCustomerIdAndMotorbikeIdAndStatus(Long customerId, Long motorbikeId, String status);

}