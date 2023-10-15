package buysellmoto.repository;

import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.entity.ShowroomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoBrandRepository extends JpaRepository<MotoBrandEntity, Long> {

}