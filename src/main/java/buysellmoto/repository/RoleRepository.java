package buysellmoto.repository;

import buysellmoto.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByIdIn(List<Long> ids);

    RoleEntity findByName(String name);

}