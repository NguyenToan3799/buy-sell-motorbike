package buysellmoto.repository;

import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.entity.MotorbikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorbikeRepository extends JpaRepository<MotorbikeEntity, Long> {

}