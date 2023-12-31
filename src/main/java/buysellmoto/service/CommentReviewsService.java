package buysellmoto.service;


import buysellmoto.core.enumeration.CommentatorTypeEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.CommentReviewsDao;
import buysellmoto.model.dto.CommentReviewsDto;
import buysellmoto.model.filter.CommentReviewsFilter;
import buysellmoto.model.mapper.CommentReviewsMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CommentReviewsService {

    @Autowired
    private CommentReviewsDao commentReviewsDao;

    public CommentReviewsDto getById(Long id) {
        return commentReviewsDao.getById(id);
    }
    
    public List<CommentReviewsDto> getAll() {
        return commentReviewsDao.getAll();
    }

    public List<CommentReviewsDto> getByCustomerReviewsId(Long customerReviewsId) {
        return commentReviewsDao.getByCustomerReviewsId(customerReviewsId);
    }

    @Transactional(rollbackOn = {Exception.class})
    public CommentReviewsDto createOne (CommentReviewsFilter filter) {
        CommentReviewsDto preparingDto = filter.getCriteria();
        if (Objects.equals(CommentatorTypeEnum.of(preparingDto.getCommentatorType()), CommentatorTypeEnum.INVALID)) {
            throw new BusinessException(ApiMessageCode.COMMENTATOR_TYPE_INVALID);
        }
        preparingDto.setId(null);
        preparingDto.setCommentDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        return commentReviewsDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public CommentReviewsDto updateOne(CommentReviewsFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        CommentReviewsDto preparingDto = filter.getCriteria();
        return commentReviewsDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        commentReviewsDao.deleteById(id);
        return true;
    }

}
