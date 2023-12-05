package buysellmoto.dao;

import buysellmoto.model.dto.CommentReviewsDto;
import buysellmoto.model.mapper.CommentReviewsMapper;
import buysellmoto.repository.CommentReviewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentReviewsDao {

    @Autowired
    private CommentReviewsRepository commentReviewsRepository;

    @Autowired
    private CommentReviewsMapper mapper;

    public CommentReviewsDto getById(Long id) {
        return mapper.toDto(commentReviewsRepository.findById(id).orElseThrow());
    }

    public List<CommentReviewsDto> getAll() {
        return mapper.toDto(commentReviewsRepository.findAll());
    }

    public List<CommentReviewsDto> getByCustomerReviewsId(Long customerReviewId) {
        return mapper.toDto(commentReviewsRepository.findAllByCustomerReviewsId(customerReviewId));
    }

    public List<CommentReviewsDto> getByCustomerReviewsIds(List<Long> customerReviewIds) {
        return mapper.toDto(commentReviewsRepository.findAllByCustomerReviewsIdIn(customerReviewIds));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CommentReviewsDto createOne(CommentReviewsDto dto) {
        return mapper.toDto(commentReviewsRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CommentReviewsDto updateOne(CommentReviewsDto dto) {
        return mapper.toDto(commentReviewsRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        commentReviewsRepository.delete(commentReviewsRepository.findById(id).orElseThrow());
        return true;
    }

}
