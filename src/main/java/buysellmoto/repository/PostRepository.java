package buysellmoto.repository;

import buysellmoto.model.entity.PostEntity;
import buysellmoto.model.entity.PurchaseAppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

}