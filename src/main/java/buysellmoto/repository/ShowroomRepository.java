package buysellmoto.repository;

import buysellmoto.model.entity.CustomerEntity;
import buysellmoto.model.entity.ShowroomEntity;
import buysellmoto.model.vo.PostProjection;
import buysellmoto.model.vo.ShowroomProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowroomRepository extends JpaRepository<ShowroomEntity, Long> {

    List<ShowroomEntity> findAllByIdIn(List<Long> ids);

    @Query(value = "SELECT s.id AS id, s.name AS name, "
            + "s.province AS province, si.url AS bannerUrl "
            + "FROM showroom s "
            + "INNER JOIN showroom_image si ON si.showroom_id = s.id "
            + "WHERE (UPPER(COALESCE(s.name, '')) LIKE CONCAT('%', UPPER(:searchValue), '%')) "
            + "AND (UPPER(COALESCE(s.province, '')) LIKE CONCAT('%', UPPER(:province), '%')) "
            + "AND si.type = 'BANNER'", nativeQuery = true)
    Page<ShowroomProjection> getPaging(Pageable pageable,
                                   @Param("searchValue") String searchValue,
                                   @Param("province") String province);

}