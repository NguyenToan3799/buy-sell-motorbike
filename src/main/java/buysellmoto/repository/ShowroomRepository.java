package buysellmoto.repository;

import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.entity.ShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowroomRepository extends JpaRepository<ShowroomEntity, Long> {

}