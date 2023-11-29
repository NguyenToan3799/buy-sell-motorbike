package buysellmoto.repository;

import buysellmoto.model.entity.PostEntity;
import buysellmoto.model.entity.PurchaseAppointmentEntity;
import buysellmoto.model.vo.PostProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = "SELECT p.id AS id, p.price AS price, p.created_date AS createdDate, "
            + "m.name AS motorbikeName, m.condition AS motorbikeCondition, "
            + "m.odo AS motorbikeOdo, m.year_of_registration AS yearOfRegistration, "
            + "s.province AS location, mb.logo AS logoBrand, mi.url AS motorbikeThumbnail, "
            + "mb.name AS brandName FROM post p "
            + "INNER JOIN motorbike m ON m.id = p.motorbike_id "
            + "INNER JOIN showroom s ON s.id = p.showroom_id "
            + "INNER JOIN moto_brand mb ON m.moto_brand_id = mb.id "
            + "LEFT JOIN motorbike_image mi ON mi.motorbike_id = m.id "
            + "WHERE ((UPPER(COALESCE(m.name, '')) LIKE CONCAT('%', UPPER(:searchValue), '%')) "
            + "OR (UPPER(COALESCE(mb.name, '')) LIKE CONCAT('%', UPPER(:searchValue), '%'))) "
            + "AND (UPPER(COALESCE(mb.name, '')) LIKE CONCAT('%', UPPER(:brandName), '%')) "
            + "AND mi.is_thumbnail = true", nativeQuery = true)
    Page<PostProjection> getPaging(Pageable pageable,
                                   @Param("searchValue") String searchValue,
                                   @Param("brandName") String brandName);

    @Query(value = "SELECT p.id AS id, p.price AS price, p.created_date AS createdDate, "
            + "m.name AS motorbikeName, m.condition AS motorbikeCondition, "
            + "m.odo AS motorbikeOdo, m.year_of_registration AS yearOfRegistration, "
            + "s.province AS location, mb.logo AS logoBrand, mi.url AS motorbikeThumbnail, "
            + "mb.name AS brandName FROM post p "
            + "INNER JOIN motorbike m ON m.id = p.motorbike_id "
            + "INNER JOIN showroom s ON s.id = p.showroom_id "
            + "INNER JOIN moto_brand mb ON m.moto_brand_id = mb.id "
            + "LEFT JOIN motorbike_image mi ON mi.motorbike_id = m.id "
            + "WHERE p.showroom_id = :showroomId "
            + "AND mi.is_thumbnail = true", nativeQuery = true)
    List<PostProjection> getPostByShowroomId(Long showroomId);

}