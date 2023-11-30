package buysellmoto.repository;

import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.entity.MotorbikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorbikeRepository extends JpaRepository<MotorbikeEntity, Long> {

    List<MotorbikeEntity> findByIdIn(List<Long> ids);
    List<MotorbikeEntity> findByShowroomId(Long showroomId);

}