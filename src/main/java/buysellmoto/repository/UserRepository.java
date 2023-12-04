package buysellmoto.repository;

import buysellmoto.model.entity.RoleEntity;
import buysellmoto.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT ui.* "
            + "FROM user_info ui "
            + "WHERE (ui.user_name = :account OR ui.email = :account OR ui.phone = :account) "
            + "AND ui.password = :password", nativeQuery = true)
    UserEntity checkLogin(String account, String password);

    Optional<UserEntity> getByEmail(String email);

    List<UserEntity> findAllByIdIn(List<Long> ids);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUserName(String phone);

    boolean existsByIdNotAndEmail(Long id, String email);

    boolean existsByIdNotAndPhone(Long id, String phone);

}