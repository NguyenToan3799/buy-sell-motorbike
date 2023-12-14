package buysellmoto.repository;

import buysellmoto.model.entity.MotorbikeEntity;
import buysellmoto.model.entity.MotorbikeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorbikeImageRepository extends JpaRepository<MotorbikeImageEntity, Long> {

    List<MotorbikeImageEntity> findByMotorbikeId(Long motorbikeId);

    List<MotorbikeImageEntity> findByMotorbikeIdIn(List<Long> motorbikeIds);

}