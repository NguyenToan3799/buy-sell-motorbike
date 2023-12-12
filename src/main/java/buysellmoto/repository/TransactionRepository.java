package buysellmoto.repository;

import buysellmoto.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByShowroomId(Long showroomId);

    List<TransactionEntity> findAllByShowroomIdAndType(Long showroomId, String type);

    List<TransactionEntity> findAllBySellRequestId(Long sellRequestId);

    List<TransactionEntity> findAllByBuyRequestId(Long buyRequestId);

}