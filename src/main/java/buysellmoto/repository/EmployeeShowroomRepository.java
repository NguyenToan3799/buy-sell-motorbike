package buysellmoto.repository;

import buysellmoto.dao.EmployeeShowroomDao;
import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.entity.EmployeeShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeShowroomRepository extends JpaRepository<EmployeeShowroomEntity, Long> {

    EmployeeShowroomEntity getByUserId(Long userId);

    List<EmployeeShowroomEntity> findAllByShowroomId(Long showroomId);
}