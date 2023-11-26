package buysellmoto.repository;

import buysellmoto.dao.RejectRequestDao;
import buysellmoto.model.entity.RejectRequestEntity;
import buysellmoto.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RejectRequestRepository extends JpaRepository<RejectRequestEntity, Long> {

    RejectRequestEntity findBySellRequestId(Long sellRequestId);

}