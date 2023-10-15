package buysellmoto.repository;

import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.entity.EmployeeShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeShowroomRepository extends JpaRepository<EmployeeShowroomEntity, Long> {

}