package buysellmoto.repository;

import buysellmoto.model.entity.CommentReviewsEntity;
import buysellmoto.model.entity.CustomerReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReviewsRepository extends JpaRepository<CommentReviewsEntity, Long> {

    List<CommentReviewsEntity> findAllByCustomerReviewsId(Long customerReviewsId);

    List<CommentReviewsEntity> findAllByCustomerReviewsIdIn(List<Long> customerReviewsIds);

}