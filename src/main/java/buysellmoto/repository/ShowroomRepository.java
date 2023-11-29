package buysellmoto.repository;

import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.entity.ShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowroomRepository extends JpaRepository<ShowroomEntity, Long> {

    List<ShowroomEntity> findAllByIdIn(List<Long> ids);

}