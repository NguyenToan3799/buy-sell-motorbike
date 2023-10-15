package buysellmoto.repository;

import buysellmoto.model.entity.MotoBrandEntity;
import buysellmoto.model.entity.MotoTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoTypeRepository extends JpaRepository<MotoTypeEntity, Long> {

}