package buysellmoto.repository;

import buysellmoto.model.entity.RequestHistoryEntity;
import buysellmoto.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistoryEntity, Long> {

    List<RequestHistoryEntity> findAllByRequestIdAndRequestType(Long requestId, String requestType);

}