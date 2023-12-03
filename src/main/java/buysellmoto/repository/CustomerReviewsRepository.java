package buysellmoto.repository;

import buysellmoto.model.entity.CustomerReviewsEntity;
import buysellmoto.model.entity.EmployeeShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerReviewsRepository extends JpaRepository<CustomerReviewsEntity, Long> {

    List<CustomerReviewsEntity> findAllByShowroomId(Long showroomId);

}