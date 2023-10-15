package buysellmoto.repository;

import buysellmoto.model.entity.MotorbikeEntity;
import buysellmoto.model.entity.MotorbikeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorbikeImageRepository extends JpaRepository<MotorbikeImageEntity, Long> {

}